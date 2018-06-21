package com.gladkova.maze;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class Desk {

    public Coord start;
    public Coord fin;
    public ArrayList<Coord> holes = new ArrayList<>();
    public char[][] desk = setHoles(processMap(initMap(0.4), 5, 2));

    public Desk() {
    }

    public Desk(Path file) {
        try {
            holes.clear();
            List<String> strings = Files.readAllLines(file);
            for (int i = 0; i < strings.size(); i++) {
                for (int j = 0; j < strings.get(i).length(); j++) {
                    desk[i][j] = strings.get(i).charAt(j);
                    if (strings.get(i).charAt(j) == 'S')
                        start = new Coord(i, j);
                    if (Character.isDigit(strings.get(i).charAt(j)))
                        holes.add(new Coord(i, j));
                    if (strings.get(i).charAt(j) == 'E')
                        fin = new Coord(i, j);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean[][] initMap(double live) {
        boolean[][] desk = new boolean[10][10];
        SecureRandom rand = new SecureRandom();
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++)
                desk[i][j] = (rand.nextDouble() < live);
        return desk;
    }

    private int countLive(boolean[][] desk, int x, int y) {
        int res = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                int tx = x + i;
                int ty = y + j;
                if (tx < 0 || tx >= desk.length || ty < 0 || ty >= desk[0].length || desk[tx][ty])
                    res++;
            }
        }
        return res;
    }

    private boolean[][] processMap(boolean[][] desk, int birth, int death) {
        boolean[][] old = desk;
        boolean[][] tmp = new boolean[10][10];
        boolean fl = false;
        while (!deskEq(old, tmp)) {
            if (fl) {
                for (int i = 0; i < old.length; i++)
                    for (int j = 0; j < old[0].length; j++)
                        old[i][j] = tmp[i][j];
            } else fl = true;
            for (int i = 0; i < old.length; i++)
                for (int j = 0; j < old[0].length; j++) {
                    int count = countLive(desk, i, j);
                    tmp[i][j] = (old[i][j] && count >= death) || (!old[i][j] && count > birth);
                }
        }
        for (int i = 0; i < old.length; i++) {
            tmp[i][0] = true;
            tmp[i][old[0].length - 1] = true;
        }

        for (int i = 0; i < old[0].length; i++) {
            tmp[0][i] = true;
            tmp[old.length - 1][i] = true;
        }
        return tmp;
    }

    private Coord getPlace(boolean[][] desk) {
        SecureRandom rnd = new SecureRandom();
        int x = 0;
        int y = 0;
        while (desk[x][y]) {
            x = rnd.nextInt(desk.length - 3) + 1;
            y = rnd.nextInt(desk[0].length - 3) + 1;
        }
        return new Coord(x, y);
    }

    private char[][] setHoles(boolean[][] desk) {
        int size = (desk.length - 2) * (desk[0].length - 2);
        SecureRandom rnd = new SecureRandom();
        int n = rnd.nextInt(10 < size ? 10 : size);
        char[][] res = new char[10][10];
        for (int i = 0; i < desk.length; i++)
            for (int j = 0; j < desk[0].length; j++)
                if (desk[i][j])
                    res[i][j] = '#';
                else
                    res[i][j] = ' ';
        for (int i = 0; i < n; i++) {
            Coord c = getPlace(desk);
            holes.add(c);
            res[c.x][c.y] = ((char) (i + '0' - 1));
        }
        Coord c = getPlace(desk);
        start = c;
        res[c.x][c.y] = 'S';
        c = getPlace(desk);
        fin = c;
        res[c.x][c.y] = 'E';
        c = getPlace(desk);
        res[c.x][c.y] = 'T';
        return res;
    }

    void printMap(char[][] map) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++)
                str.append(map[i][j]);
            str.append('\n');
        }
        System.out.println(str.toString());
    }

    private boolean deskEq(boolean[][] fst, boolean[][] scd) {
        if (fst.length != scd.length || fst[0].length != scd[0].length)
            return false;
        for (int i = 0; i < fst.length; i++)
            for (int j = 0; j < scd.length; j++)
                if (fst[i][j] != scd[i][j])
                    return false;
        return true;
    }
}
