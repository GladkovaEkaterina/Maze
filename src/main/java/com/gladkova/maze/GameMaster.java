package com.gladkova.maze;

import java.io.File;
import java.security.SecureRandom;

public class GameMaster {
    private Desk desk;
    private Player player;
    private boolean game;
    private Coord actual;
    private int hPointer;
    private boolean debug;
    //класс ведущего


    public GameMaster() {
        desk = new Desk();
        player = new Player();
        game = true;
        actual = desk.start;
    }

    GameMaster(File fl, boolean debug) {
        this();
        desk = new Desk(fl.toPath());
        actual = desk.start;
        desk.printMap(desk.desk);
        this.debug = debug;
    }

    private Coord genCoord(Player.Orient orient) {
        switch (orient) {
            case UP:
                return new Coord(actual.x - 1, actual.y);
            case DOWN:
                return new Coord(actual.x + 1, actual.y);
            case LEFT:
                return new Coord(actual.x, actual.y - 1);
            case RIGHT:
                return new Coord(actual.x, actual.y + 1);
        }
        return null;
    }

    private void useHole(Coord nCoord) {
        if (((int) desk.desk[nCoord.x][nCoord.y]) - '0' + 1 < desk.holes.size())
            hPointer = ((int) desk.desk[nCoord.x][nCoord.y]) - '0' + 1;
        else
            hPointer = 0;
        actual = desk.holes.get(hPointer);
        if (new SecureRandom().nextInt() % 2 == 0 || debug)
            System.out.println("You've entered the " + hPointer + " portal.");
        else
            System.out.println("Are rabbit holes back in fashion?! You fell into the " + hPointer + " portal");
    }

    public void start() {
        System.out.println("Hey, bum. And you came for my money?");
        while (game) {
            System.out.print("gm>");
            Player.Move m = player.getMove();
            Coord nCoord;
            if (!m.suc) System.out.println("Are you alright?");
            else
                switch (m.t) {
                    case WAIT:
                        System.out.println("You five minutes standing like a statue");
                        if (Character.isDigit(desk.desk[actual.x][actual.y]))
                            useHole(actual);
                        break;
                    case GRAB:
                        if (desk.desk[actual.x][actual.y] != 'T' && desk.desk[actual.x][actual.y] != 'E') {
                            System.out.println("Emptiness is a good find, young padawan");
                            break;
                        } else if (desk.desk[actual.x][actual.y] == 'T') {
                            player.loot++;
                            System.out.println("Oh, someone found the treasure... But will he carry it out?)");
                            break;
                        } else if (desk.desk[actual.x][actual.y] == 'E') {
                            if (player.loot > 0) {
                                System.out.println("Someone came out of the maze... I'll call Forbes.");
                                game = !game;
                                break;
                            } else {
                                System.out.println("Sufferer, why would you out if you're without a penny in your pocket?");
                                player.loot++;
                                desk.desk[actual.x][actual.y] = ' ';
                                break;
                            }
                        } else {
                            System.out.println("Someone wrong");
                            break;
                        }
                    case BOOM:
                        nCoord = genCoord(m.o);
                        if (desk.desk[nCoord.x][nCoord.y] == '#') {
                            if (player.bomb > 0) {
                                desk.desk[nCoord.x][nCoord.y] = ' ';
                                if (new SecureRandom().nextInt() % 2 == 0 || debug)
                                    System.out.println("Boy, that was my favorite wall!");
                                else
                                    System.out.println("One section of the wall, and the noise of the corporate accounting");
                                player.bomb--;
                            } else {
                                System.out.println("Who in here farted?! That's you... You're out of bombs, boy. ");
                            }
                            break;
                        } else {
                            if (new SecureRandom().nextInt() % 2 == 0 || debug)
                                System.out.println("Boy, you must have messed up the game. It was in the bomber had to blow up the empty cells.");
                            else
                                System.out.println("Eh, why? Nothing has been achieved, but the floor is scratched! A rental grinders are now expensive!");
                            break;
                        }
                    case MOVE:
                        nCoord = genCoord(m.o);
                        if (desk.desk[nCoord.x][nCoord.y] == ' ' || desk.desk[nCoord.x][nCoord.y] == 'T'
                                || desk.desk[nCoord.x][nCoord.y] == 'E' || Character.isDigit(desk.desk[nCoord.x][nCoord.y])) {
                            switch (m.o) {
                                case UP:
                                    System.out.println("But the North wind blows us in the back");
                                    break;
                                case LEFT:
                                    System.out.println("Be careful, they say there are launch Gadfly");
                                    break;
                                case RIGHT:
                                    System.out.println("Life hung by a thread.\n" +
                                            "Shah! And a body on a slippery Board.\n" +
                                            "The steel wanted a drink of blood.\n" +
                                            "Steel wheezed \" Go East!\"");
                                    break;
                                case DOWN:
                                    System.out.println("Walk along the tracks to the South,\n" +
                                            "Where the sun, sea, air,\n" +
                                            "And moon.");
                                    break;
                            }
                            actual = nCoord;
                            if (Character.isDigit(desk.desk[nCoord.x][nCoord.y])) {
                                useHole(nCoord);
                            }
                            if (desk.desk[nCoord.x][nCoord.y] == 'T' || desk.desk[nCoord.x][nCoord.y] == 'E')
                                System.out.println("Boy, stop tripping!");
                        } else {
                            System.out.println("It seems only children and wrestlers butt a wall... On a fighter you don't like, so get to pack a bag!");
                        }
                }
        }
    }
}
