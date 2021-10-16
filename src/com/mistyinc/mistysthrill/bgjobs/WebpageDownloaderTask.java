package com.mistyinc.mistysthrill.bgjobs;

import com.mistyinc.mistysthrill.dao.BookmarkDao;
import com.mistyinc.mistysthrill.entities.WebLink;
import com.mistyinc.mistysthrill.util.HttpConnect;
import com.mistyinc.mistysthrill.util.IOUtil;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class WebpageDownloaderTask implements Runnable {
    private static BookmarkDao dao = new BookmarkDao();
    private static final long TIME_FRAME = 3000000000L;
    private boolean downloadAll = false;

    ExecutorService downloadExecutor = Executors.newFixedThreadPool(5);

    public WebpageDownloaderTask(boolean downloadAll) {
        this.downloadAll = downloadAll;
    }

    private static class Downloader<T extends WebLink> implements Callable<T> {
        private T weblink;

        public Downloader(T weblink) {
            this.weblink = weblink;
        }

        @Override
        public T call() {
            String htmlPage = null;
            try {
                if (!weblink.getUrl().endsWith(".pdf")) {
                    weblink.setDownloadStatus(WebLink.DownloadStatus.FAILED);
                    htmlPage = HttpConnect.download(weblink.getUrl());
                    weblink.setHtmlPage(htmlPage);
                } else {
                    weblink.setDownloadStatus(WebLink.DownloadStatus.NOT_ELIGIBLE);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            return weblink;
        }
    }

    private List<WebLink> getWebLinks() {
        List<WebLink> webLinks = null;

        if (downloadAll) {
            webLinks = dao.getAllWebLinks();
            downloadAll = false;
        } else {
            webLinks = dao.getWebLinks(WebLink.DownloadStatus.NOT_ATTEMPTED);
        }

        return webLinks;
    }

    //converting all task to callables
    private List<Downloader<WebLink>> getTask(List<WebLink> webLinks) {
        List<Downloader<WebLink>> tasks = new ArrayList<>();

        webLinks.forEach(webLink -> tasks.add(new Downloader<WebLink>(webLink)));

        return tasks;
    }

    private void download(List<WebLink> webLinks) {
        List<Downloader<WebLink>> tasks = getTask(webLinks);
        List<Future<WebLink>> futures = new ArrayList<>();

        try {
            futures = downloadExecutor.invokeAll(tasks, TIME_FRAME, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        futures.forEach(webLinkFuture -> {

            try {
                if (!webLinkFuture.isCancelled()) {
                    WebLink webLink = null;

                    webLink = webLinkFuture.get();
                    String webPage = webLink.getHtmlPage();
                    if (webPage != null) {
                        IOUtil.write(webPage, webLink.getId());
                        webLink.setDownloadStatus(WebLink.DownloadStatus.SUCCESS);
                        System.out.println("Download Successful: " + webLink.getUrl());
                    }else {
                        System.out.println("Unable to download webpage: " + webLink.getUrl());
                    }
                } else {
                    System.out.println();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }


        });
    }

    @Override
    public void run() {

        while (!Thread.currentThread().isInterrupted()) {
            //collecting all weblinks
            List<WebLink> webLinks = getWebLinks();

            //downloading weblinks of interest
            if (webLinks.size() > 0) {
                download(webLinks);
            } else {
                System.out.println("No new Web Links to download");
            }

            //stalling for a bit so new links can be added for processing
            try {
                TimeUnit.SECONDS.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        downloadExecutor.shutdown();

    }


}
