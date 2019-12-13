package anse.Worlds.libs;

import anse.Worlds.PluginGui;
import cn.nukkit.Player;

import java.util.HashMap;

public abstract class MyCallable{

    public Player player;
    public PluginGui gui;
    public HashMap<String, Object> use;

    private HashMap<Integer, Object> hashMap;

    public void setHashMap(HashMap<Integer, Object> hashMap){
        this.hashMap = hashMap;
    }

    public HashMap<Integer, Object> getHashMap(){
        return this.hashMap;
    }

    public int getButtonId(){
        return Integer.valueOf(String.valueOf(this.hashMap.get(0)));
    }

    public String getButtonText(){
        return String.valueOf(this.hashMap.get(1));
    }

    public MyCallable(Player player, PluginGui gui) {
        this(player, gui, new HashMap<>());
    }

    public MyCallable(Player player, PluginGui gui, HashMap<String, Object> use) {
        this.player = player;
        this.gui = gui;
        this.use = use;
    }

    public abstract void call();
}
