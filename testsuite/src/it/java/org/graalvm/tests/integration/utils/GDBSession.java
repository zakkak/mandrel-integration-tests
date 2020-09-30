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
 * GDB commands and expected output
 *
 * @author Michal Karm Babacek <karm@redhat.com>
 */
public enum GDBSession {
    NONE(new CP[]{}),
    DEBUG_SYMBOLS_SMOKE(new CP[]{
            new CP("run < ./test_data_small.txt\n",
                    Pattern.compile(".*fdc7c50f390c145bc58a0bedbe5e6d2e35177ac73d12e2b23df149ce496a5572.*exited normally.*", Pattern.DOTALL)),
            new CP("info functions .*smoke.*\n",
                    Pattern.compile(
                            ".*File debug_symbols_smoke/ClassA.java:.*" +
                                    "void debug_symbols_smoke.ClassA::toString\\(\\)void;.*" +
                                    "File debug_symbols_smoke/Main\\$\\$Lambda\\$.*.java:.*" +
                                    "void debug_symbols_smoke.Main..Lambda..*::accept\\(java.lang.Object\\)\\(\\)void;.*" +
                                    "File debug_symbols_smoke/Main.java:.*" +
                                    "void debug_symbols_smoke.Main::lambda\\$thisIsTheEnd\\$0\\(java.io.ByteArrayOutputStream, debug_symbols_smoke.ClassA\\)\\(\\)void;.*" +
                                    "void debug_symbols_smoke.Main::main\\(java.lang.String\\[\\]\\)\\(\\)void;.*" +
                                    "void debug_symbols_smoke.Main::thisIsTheEnd\\(java.util.List\\)\\(\\)void;.*"
                            , Pattern.DOTALL)),
            new CP("break Main.java:70\n",
                    Pattern.compile(".*Breakpoint 1 at .*: file debug_symbols_smoke/Main.java, line 70.*", Pattern.DOTALL)),
            new CP("break Main.java:71\n",
                    Pattern.compile(".*Breakpoint 2 at .*: file debug_symbols_smoke/Main.java, line 71.*", Pattern.DOTALL)),
            new CP("break Main.java:72\n",
                    Pattern.compile(".*Breakpoint 3 at .*: file debug_symbols_smoke/Main.java, line 72.*", Pattern.DOTALL)),
            new CP("run < ./test_data_small.txt\n",
                    Pattern.compile(".*Breakpoint 1, .*while \\(sc.hasNextLine\\(\\)\\).*", Pattern.DOTALL)),
            new CP("c\n",
                    Pattern.compile(".*Breakpoint 3, debug_symbols_smoke.Main::main.*at debug_symbols_smoke/Main.java:76.*String l = sc.nextLine\\(\\);.*", Pattern.DOTALL)),
            new CP("c\n",
                    Pattern.compile(".*Breakpoint 2, debug_symbols_smoke.Main::main.*at debug_symbols_smoke/Main.java:71.* if \\(myString != null.*", Pattern.DOTALL)),
            new CP("c\n",
                    Pattern.compile(".*Breakpoint 2, debug_symbols_smoke.Main::main.*at debug_symbols_smoke/Main.java:71.* if \\(myString != null.*", Pattern.DOTALL)),
            new CP("d 2\n",
                    Pattern.compile(".*", Pattern.DOTALL)),
            new CP("c\n",
                    Pattern.compile(".*fdc7c50f390c145bc58a0bedbe5e6d2e35177ac73d12e2b23df149ce496a5572.*exited normally.*", Pattern.DOTALL)),
            new CP("list ClassA.java:30\n",
                    Pattern.compile(".*ClassA\\(int myNumber, String myString\\).*", Pattern.DOTALL)),
    }),
    DEBUG_QUARKUS_FULL_MICROPROFILE(new CP[]{
            new CP("b ConfigTestController.java:33\n",
                    Pattern.compile(".*Breakpoint 1 at .*: file com/example/quarkus/config/ConfigTestController.java, line 33.*", Pattern.DOTALL)),
            new CP("run&\n",
                    Pattern.compile(".*Installed features:.*", Pattern.DOTALL)),
            // TODO: This is not portable...but then again, is it a problem while we are in gdb already?
            new CP("shell curl http://localhost:8080/data/config/lookup &\n",
                    Pattern.compile(".*hit Breakpoint 1,.*com.example.quarkus.config.ConfigTestController::getLookupConfigValue\\(\\)void.*\\(\\).*at.*" +
                            "com/example/quarkus/config/ConfigTestController.java:33.*String value = config.getValue\\(\"value\", String.class\\);.*",
                            Pattern.DOTALL)),
            new CP("c&\n",
                    Pattern.compile(".*Continuing.*", Pattern.DOTALL)),
    });

    public final CP[] gdbOutput;

    GDBSession(CP[] gdbOutput) {
        this.gdbOutput = gdbOutput;
    }
}
