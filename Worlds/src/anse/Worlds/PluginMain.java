package anse.Worlds;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Level;
import cn.nukkit.level.format.LevelProvider;
import cn.nukkit.level.format.generic.BaseLevelProvider;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;

import java.io.File;

public class PluginMain extends PluginBase {

    public static final String NAME = TextFormat.BOLD + "[Worlds] " + TextFormat.RESET + TextFormat.BOLD + TextFormat.ITALIC;

    private PluginGui gui;

    @Override
    public void onEnable() {
        // 加载所有世界
        File map_folder = new File(this.getServer().getDataPath() + "/worlds");
        if(map_folder.isDirectory()) {
            File[] files = map_folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        String level_name = file.getName();
                        if(!this.getServer().isLevelLoaded(level_name)){
                            if(!this.getServer().isLevelGenerated(level_name)){
                                this.getLogger().info("开始生成 " + level_name + " 地图!");
                                this.getServer().generateLevel(level_name);
                                this.getLogger().info("地图 " + level_name + " 生成完毕!");
                            }
                            this.getServer().loadLevel(level_name);
                            this.getLogger().info("地图 " + level_name + " 加载成功!");
                        }
                    }
                }
            }
        }
        this.gui = new PluginGui(this);
        this.getLogger().info("加载成功......");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.isPlayer()){
            this.gui.sendGui((Player) sender);
        }
        return true;
    }
}
