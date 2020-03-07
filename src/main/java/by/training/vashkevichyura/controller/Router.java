package by.training.vashkevichyura.controller;

public class Router {
    private String path;
    private RouterType type;

    public Router(String path, RouterType type) {
        this.path = path;
        this.type = type;
    }

    public Router() {
    }

    public String getPath() {
        return path;
    }

    public RouterType getType() {
        return type;
    }

    public enum RouterType {
        REDIRECT,
        FORWARD
    }
}
