package com.gladkova.maze;

import java.util.Scanner;

public class Player {
    //Описывает игрока
    int hp;
    int bomb;
    int loot;

    public Player() {
        hp = 100;
        bomb = 3;
        loot = 0;
    }

    private Orient getOrient(String str) {
        if (str.equals("UP")) return Orient.UP;
        if (str.equals("DOWN")) return Orient.DOWN;
        if (str.equals("LEFT")) return Orient.LEFT;
        if (str.equals("RIGHT")) return Orient.RIGHT;
        return null;
    }

    public Move getMove() {
        //Получает ввод пользователя и заносит его в класс move заполняя поля enumами.
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine().toUpperCase();
        if (str.split(" ").length == 1) {
            if (str.split(" ")[0].equals("WAIT"))
                return new Move(Type.WAIT, null);
            if (str.split(" ")[0].equals("GRAB"))
                return new Move(Type.GRAB, null);
        } else if (str.split(" ").length == 2) {
            if (str.split(" ")[0].equals("MOVE"))
                return new Move(Type.MOVE, getOrient(str.split(" ")[1]));
            if (str.split(" ")[0].equals("BOOM"))
                return new Move(Type.BOOM, getOrient(str.split(" ")[1]));
        }
        return new Move();
    }

    public enum Type {
        MOVE, BOOM, WAIT, GRAB
    }

    public enum Orient {
        UP, DOWN, LEFT, RIGHT
    }

    class Move {
        boolean suc = false;
        Type t = null;
        Orient o = null;

        public Move(Type type, Orient orient) {
            t = type;
            o = orient;
            suc = true;
        }

        public Move() {
        }
    }
}
