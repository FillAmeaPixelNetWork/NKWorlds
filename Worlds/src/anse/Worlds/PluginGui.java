package anse.Worlds;

import anse.Worlds.libs.MyCallable;
import anse.Worlds.libs.MyForm;
import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.level.format.LevelProvider;
import cn.nukkit.level.format.generic.BaseLevelProvider;
import cn.nukkit.utils.TextFormat;

import java.util.Map;

public class PluginGui {

    private static final int NUMBER = 8;

    private PluginMain main;

    public PluginGui(PluginMain main) {
        this.main = main;
    }

    public void sendGui(Player player){
        FormWindowSimple form = new FormWindowSimple(TextFormat.BOLD + "�����б�", "");
        for(Map.Entry<Integer, Level> entry: this.main.getServer().getLevels().entrySet()){
            ElementButton button = new ElementButton(entry.getValue().getFolderName());
            button.addImage(new ElementButtonImageData("path", "textures/ui/World"));
            form.addButton(button);
        }
        MyForm my_form = new MyForm(form, new MyCallable(player, this) {
            @Override
            public void call() {
                String level_name = this.getButtonText();
                Level level = this.gui.main.getServer().getLevelByName(level_name);
                if(level != null){
                    if (!level.getName().equals(level.getFolderName())) {
                        this.gui.main.getLogger().info("�����޸� " + level.getName() + " ��ͼlevel.dat�ļ�!");
                        LevelProvider provider = level.getProvider();
                        if (provider instanceof BaseLevelProvider) {
                            ((BaseLevelProvider) provider).getLevelData().putString("LevelName", level.getFolderName());
                            provider.saveLevelData();
                            this.gui.main.getLogger().info("�޸� " + level.getName() + " ��ͼlevel.dat�ļ��ɹ�!");
                        }
                    }
                    Position spawn = level.getSafeSpawn();
                    int number = 0;
                    while (spawn == null && number < NUMBER){
                        number += 1;
                        spawn = level.getSafeSpawn();
                    }
                    if(spawn != null) {
                        this.player.teleport(spawn.add(0.5d, 2.0d, 0.5d));
                        this.player.sendMessage(TextFormat.BOLD + PluginMain.NAME + TextFormat.GREEN + "�ɹ����͵� " + TextFormat.WHITE + level_name + TextFormat.GREEN + " ��ͼ!");
                    }else{
                        this.player.sendMessage(TextFormat.BOLD + PluginMain.NAME + TextFormat.RED + "����ʧ��, δ�ҵ� " + TextFormat.WHITE + level_name + TextFormat.RED + " ��ͼ�İ�ȫ������!");
                    }
                }else{
                    this.player.sendMessage(TextFormat.BOLD + PluginMain.NAME + TextFormat.RED + "����ʧ��, δ���� " + TextFormat.WHITE + level_name + TextFormat.RED + " ��ͼ!");
                }
            }
        });
        my_form.sendToPlayer(player);
    }
}
