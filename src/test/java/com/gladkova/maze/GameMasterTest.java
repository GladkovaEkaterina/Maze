package com.gladkova.maze;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.Assert.assertEquals;

public class GameMasterTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();
    @Rule
    public SystemOutRule systemOut = new SystemOutRule().enableLog();
    @Rule
    public TextFromStandardInputStream systemIn = TextFromStandardInputStream.emptyStandardInputStream();

    GameMaster gameMaster = null;

    @Before
    public void prepare() throws IOException {
        File fl = folder.newFile();
        Files.write(fl.toPath(), (
                "##########\n" +
                        "##  ##   #\n" +
                        "####T    #\n" +
                        "#### ## ##\n" +
                        "##### #  #\n" +
                        "####0E   #\n" +
                        "#  S    ##\n" +
                        "#  # 1   #\n" +
                        "## # #  ##\n" +
                        "##########").getBytes());
        gameMaster = new GameMaster(fl, true);
    }

    @Test
    public void start() {
        systemOut.clearLog();
        systemIn.provideLines("move right", "move up", "move up", "move up", "move right", "move right", "move up", "move up", "boom left", "move left", "boom left", "move left", "move left", "move up",
                "grab", "move right", "move right", "move right", "move down", "move down", "move down", "move left", "move left", "grab");
        gameMaster.start();
        assertEquals("Hey, bum. And you came for my money?\n" +
                "gm>Life hung by a thread.\n" +
                "Shah! And a body on a slippery Board.\n" +
                "The steel wanted a drink of blood.\n" +
                "Steel wheezed \" Go East!\"\n" +
                "gm>But the North wind blows us in the back\n" +
                "You've entered the 1 portal.\n" +
                "gm>But the North wind blows us in the back\n" +
                "gm>But the North wind blows us in the back\n" +
                "Boy, stop tripping!\n" +
                "gm>Life hung by a thread.\n" +
                "Shah! And a body on a slippery Board.\n" +
                "The steel wanted a drink of blood.\n" +
                "Steel wheezed \" Go East!\"\n" +
                "gm>Life hung by a thread.\n" +
                "Shah! And a body on a slippery Board.\n" +
                "The steel wanted a drink of blood.\n" +
                "Steel wheezed \" Go East!\"\n" +
                "gm>But the North wind blows us in the back\n" +
                "gm>But the North wind blows us in the back\n" +
                "gm>Boy, that was my favorite wall!\n" +
                "gm>Be careful, they say there are launch Gadfly\n" +
                "gm>Boy, that was my favorite wall!\n" +
                "gm>Be careful, they say there are launch Gadfly\n" +
                "gm>Be careful, they say there are launch Gadfly\n" +
                "gm>But the North wind blows us in the back\n" +
                "Boy, stop tripping!\n" +
                "gm>Oh, someone found the treasure... But will he carry it out?)\n" +
                "gm>Life hung by a thread.\n" +
                "Shah! And a body on a slippery Board.\n" +
                "The steel wanted a drink of blood.\n" +
                "Steel wheezed \" Go East!\"\n" +
                "gm>Life hung by a thread.\n" +
                "Shah! And a body on a slippery Board.\n" +
                "The steel wanted a drink of blood.\n" +
                "Steel wheezed \" Go East!\"\n" +
                "gm>Life hung by a thread.\n" +
                "Shah! And a body on a slippery Board.\n" +
                "The steel wanted a drink of blood.\n" +
                "Steel wheezed \" Go East!\"\n" +
                "gm>Walk along the tracks to the South,\n" +
                "Where the sun, sea, air,\n" +
                "And moon.\n" +
                "gm>Walk along the tracks to the South,\n" +
                "Where the sun, sea, air,\n" +
                "And moon.\n" +
                "gm>Walk along the tracks to the South,\n" +
                "Where the sun, sea, air,\n" +
                "And moon.\n" +
                "gm>Be careful, they say there are launch Gadfly\n" +
                "gm>Be careful, they say there are launch Gadfly\n" +
                "Boy, stop tripping!\n" +
                "gm>Someone came out of the maze... I'll call Forbes.\n", systemOut.getLogWithNormalizedLineSeparator());
    }
}