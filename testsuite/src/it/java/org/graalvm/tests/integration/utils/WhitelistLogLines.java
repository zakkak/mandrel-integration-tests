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

import java.util.regex.Pattern;

/**
 * Whitelists errors in log files.
 *
 * @author Michal Karm Babacek <karm@redhat.com>
 */
public enum WhitelistLogLines {

    // This is appended to all undermentioned listings
    ALL(new Pattern[]{
            // https://github.com/graalvm/mandrel/issues/125
            Pattern.compile(".*Using an older version of the labsjdk-11.*"),
            // Harmless download, e.g.
            // Downloaded from central: https://repo.maven.apache.org/maven2/org/apache/maven/maven-error-diagnostics...
            Pattern.compile(".*maven-error-diagnostics.*"),
            // Download https://repo.maven.apache.org/maven2/com/google/errorprone
            Pattern.compile(".*com/google/errorprone/error_prone.*"),
            // JDK:
            Pattern.compile("WARNING.* reflective access.*"),
            Pattern.compile("WARNING: All illegal access operations.*"),
            Pattern.compile("WARNING: Please consider reporting this to the maintainers of com.google.inject.internal.cglib.*")
    }),

    NONE(new Pattern[]{}),

    MICRONAUT_HELLOWORLD(new Pattern[]{
            // Maven shade plugin warning, harmless.
            Pattern.compile(".*Discovered module-info.class. Shading will break its strong encapsulation.*"),
            // https://github.com/oracle/graal/blob/master/substratevm/src/com.oracle.svm.core/src/com/oracle/svm/core/jdk/VarHandleFeature.java#L199
            Pattern.compile(".*GR-10238.*"),
            // A Windows specific warning
            Pattern.compile(".*Failed to create WindowsAnsiOutputStream.*")
    }),

    IMAGEIO_BUILDER_IMAGE(new Pattern[]{
            // Dnf warnings...
            Pattern.compile(".*librhsm-WARNING.*")
    }),

    QUARKUS_FULL_MICROPROFILE(new Pattern[]{
            // Some artifacts names...
            Pattern.compile(".*maven-error-diagnostics.*"),
            Pattern.compile(".*errorprone.*"),
            // Well, the RestClient demo probably should do some cleanup before shutdown...?
            Pattern.compile(".*Closing a class org.jboss.resteasy.client.jaxrs.engines.ApacheHttpClient.*"),
            // https://github.com/oracle/graal/blob/master/substratevm/src/com.oracle.svm.core/src/com/oracle/svm/core/jdk/VarHandleFeature.java#L199
            Pattern.compile(".*GR-10238.*"),
            // Unused argument on new Graal; Quarkus uses it for backward compatibility.
            Pattern.compile(".*Ignoring server-mode native-image argument --no-server.*"),
            // Windows specific warning
            Pattern.compile(".*oracle/graal/issues/2387.*"),
            // Windows specific warning, O.K.
            Pattern.compile(".*objcopy executable not found in PATH.*"),
            Pattern.compile(".*That will result in a larger native image.*"),
            Pattern.compile(".*That also means that resulting native executable is larger.*"),
            Pattern.compile(".*contain duplicate files, e.g. javax/activation/ActivationDataFlavor.class.*"),
            Pattern.compile(".*contain duplicate files, e.g. javax/servlet/http/HttpUtils.class.*"),
            Pattern.compile(".*contain duplicate files, e.g. javax/annotation/ManagedBean.class.*"),
            // Jaeger Opentracing initialization, Quarkus 2.x specific issue.
            Pattern.compile(".*io.jaegertracing.internal.exceptions.SenderException:.*"),
            // Jaeger Opentracing, Quarkus 2.x specific issue.
            Pattern.compile(".*MpPublisherMessageBodyReader is already registered.*"),
            // Params quirk, harmless
            Pattern.compile(".*Unrecognized configuration key.*quarkus.home.*was provided.*"),
            Pattern.compile(".*Unrecognized configuration key.*quarkus.version.*was provided.*"),
            // GitHub workflow Windows executor flaw:
            Pattern.compile(".*Unable to make the Vert.x cache directory.*"),
            // Not sure, definitely not Mandrel related though
            Pattern.compile(".*xml-apis:xml-apis:jar:.* has been relocated to xml-apis:xml-apis:jar:.*"),
            Pattern.compile(".*io.quarkus:quarkus-vertx-web:jar:.* has been relocated to io.quarkus:quarkus-reactive-routes:jar:.*")
    }),

    DEBUG_QUARKUS_BUILDER_IMAGE_VERTX(new Pattern[]{
            // https://github.com/oracle/graal/blob/master/substratevm/src/com.oracle.svm.core/src/com/oracle/svm/core/jdk/VarHandleFeature.java#L199
            Pattern.compile(".*GR-10238.*"),
            // Container image build
            Pattern.compile(".*lib.*-WARNING .*"),
            // Params quirk, harmless
            Pattern.compile(".*Unrecognized configuration key.*quarkus.home.*was provided.*"),
            Pattern.compile(".*Unrecognized configuration key.*quarkus.version.*was provided.*"),
            // Specific Podman version warning about the way we start gdb in an already running container; harmless.
            Pattern.compile(".*The --tty and --interactive flags might not work properly.*"),
            // Expected part of the app log
            Pattern.compile(".*'table \"fruits\" does not exist, skipping'.*"),
            // Not sure, definitely not Mandrel related though
            Pattern.compile(".*xml-apis:xml-apis:jar:.* has been relocated to xml-apis:xml-apis:jar:.*"),
            Pattern.compile(".*io.quarkus:quarkus-vertx-web:jar:.* has been relocated to io.quarkus:quarkus-reactive-routes:jar:.*")
    }),

    HELIDON_QUICKSTART_SE(new Pattern[]{
            // Unused argument on new Graal
            Pattern.compile(".*Ignoring server-mode native-image argument --no-server.*")
    }),

    QUARKUS_BUILDER_IMAGE_ENCODING(new Pattern[]{
            // Params quirk, harmless
            Pattern.compile(".*Unrecognized configuration key.*quarkus.home.*was provided.*"),
            Pattern.compile(".*Unrecognized configuration key.*quarkus.version.*was provided.*"),
    }),

    JFR(new Pattern[]{
            // https://github.com/oracle/graal/issues/3636
            Pattern.compile(".*Unable to commit. Requested size [0-9]* too large.*"),
    });

    public final Pattern[] errs;

    WhitelistLogLines(Pattern[] errs) {
        this.errs = errs;
    }
}
