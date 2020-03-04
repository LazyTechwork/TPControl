package ru.lazytechwork.tpcontrol.data;

public class TeleportCount {
    private String nickname;
    private int count = 0;

    public TeleportCount(String nickname, int count) {
        this.nickname = nickname;
        this.count = count;
    }

    public void increase(int count) {
        this.count += count;
    }

    public void increase() {
        this.count++;
    }

    public void decrease(int count) {
        this.count -= count;
    }

    public void decrease() {
        this.count--;
    }

    @Override
    public String toString() {
        return this.nickname + ": " + this.count;
    }
}
