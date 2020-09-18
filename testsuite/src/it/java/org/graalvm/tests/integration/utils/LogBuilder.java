/*
 * Copyright (c) 2020, Red Hat Inc. All rights reserved.
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.graalvm.tests.integration.utils;

import java.util.Objects;

/**
 * @author Michal Karm Babacek <karm@redhat.com>
 */
public class LogBuilder {

    public static class Log {
        public final String headerCSV;
        public final String headerMarkdown;
        public final String lineCSV;
        public final String lineMarkdown;

        public Log(String headerCSV, String headerMarkdown, String lineCSV, String lineMarkdown) {
            this.headerCSV = headerCSV;
            this.headerMarkdown = headerMarkdown;
            this.lineCSV = lineCSV;
            this.lineMarkdown = lineMarkdown;
        }
    }

    private static final String buildTimeMsHeader = "buildTimeMs";
    private long buildTimeMs = -1L;
    private static final String timeToFirstOKRequestMsHeader = "timeToFirstOKRequestMs";
    private long timeToFirstOKRequestMs = -1L;
    private static final String timeToFinishMsHeader = "timeToFinishMs";
    private long timeToFinishMs = -1L;
    private static final String rssKbHeader = "RSSKb";
    private long rssKb = -1L;
    private static final String openedFilesHeader = "FDs";
    private long openedFiles = -1L;
    private static final String appHeader = "App";
    private String app = null;

    public LogBuilder buildTimeMs(long buildTimeMs) {
        if (buildTimeMs <= 0) {
            throw new IllegalArgumentException("buildTimeMs must be a positive long, was: " + buildTimeMs);
        }
        this.buildTimeMs = buildTimeMs;
        return this;
    }

    public LogBuilder timeToFirstOKRequestMs(long timeToFirstOKRequestMs) {
        if (timeToFirstOKRequestMs <= 0) {
            throw new IllegalArgumentException("timeToFirstOKRequestMs must be a positive long, was: " + timeToFirstOKRequestMs);
        }
        this.timeToFirstOKRequestMs = timeToFirstOKRequestMs;
        return this;
    }

    public LogBuilder timeToFinishMs(long timeToFinishMs) {
        if (timeToFinishMs <= 0) {
            throw new IllegalArgumentException("timeToFinishMs must be a positive long, was: " + timeToFinishMs);
        }
        this.timeToFinishMs = timeToFinishMs;
        return this;
    }

    public LogBuilder rssKb(long rssKb) {
        if (rssKb <= 0) {
            throw new IllegalArgumentException("rssKb must be a positive long, was: " + rssKb);
        }
        this.rssKb = rssKb;
        return this;
    }

    public LogBuilder openedFiles(long openedFiles) {
        if (openedFiles <= 0) {
            throw new IllegalArgumentException("openedFiles must be a positive long, was: " + openedFiles);
        }
        this.openedFiles = openedFiles;
        return this;
    }

    public LogBuilder app(Apps app) {
        Objects.requireNonNull(app, "Valid app flavour must be provided");
        this.app = app.toString();
        return this;
    }

    public LogBuilder app(String app) {
        Objects.requireNonNull(app, "Valid app flavour must be provided");
        this.app = app;
        return this;
    }

    public Log build() {
        StringBuilder h = new StringBuilder(512);
        StringBuilder l = new StringBuilder(512);
        int sections = 0;
        if (app != null) {
            h.append(appHeader);
            h.append(',');
            l.append(app);
            l.append(',');
            sections++;
        }
        if (buildTimeMs != -1L) {
            h.append(buildTimeMsHeader);
            h.append(',');
            l.append(buildTimeMs);
            l.append(',');
            sections++;
        }
        if (timeToFirstOKRequestMs != -1L) {
            h.append(timeToFirstOKRequestMsHeader);
            h.append(',');
            l.append(timeToFirstOKRequestMs);
            l.append(',');
            sections++;
        }
        if (timeToFinishMs != -1L) {
            h.append(timeToFinishMsHeader);
            h.append(',');
            l.append(timeToFinishMs);
            l.append(',');
            sections++;
        }
        if (rssKb != -1L) {
            h.append(rssKbHeader);
            h.append(',');
            l.append(rssKb);
            l.append(',');
            sections++;
        }
        if (openedFiles != -1L) {
            h.append(openedFilesHeader);
            h.append(',');
            l.append(openedFiles);
            l.append(',');
            sections++;
        }
        String header = h.toString();
        // Strip trailing ',' for CSV
        String headerCSV = header.substring(0, header.length() - 1);
        String headerMarkdown = "|" + header.replaceAll(",", "|") + "\n|" + " --- |".repeat(sections);
        String line = l.toString();
        String lineCSV = line.substring(0, line.length() - 1);
        String lineMarkdown = "|" + line.replaceAll(",", "|");
        return new Log(headerCSV, headerMarkdown, lineCSV, lineMarkdown);
    }
}
