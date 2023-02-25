package cn.daogecmd.emojink;

import cn.daogecmd.emojink.api.EmojiAPI;
import cn.daogecmd.emojink.command.EmojiCommand;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.resourcepacks.ResourcePackManager;
import cn.nukkit.utils.Config;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Loader extends PluginBase {

    private static final UUID PACK_UUID = UUID.fromString("eef1262f-003b-41bd-94f0-b0b61e34b1f6");

    @Override
    public void onEnable() {
        this.getServer().getNetwork().registerPacket(SpawnParticleEffectPacketC.NETWORK_ID, SpawnParticleEffectPacketC.class);
        ResourcePackManager resourcePackManager = this.getServer().getResourcePackManager();
        //lack resource pack
        if (resourcePackManager.getPackById(PACK_UUID) == null) {
            this.getLogger().warning("Specific resource pack is missing!");
        }
        //save emoji.yml
        saveResource("emoji.yml");
        Map<String, String> emojiList = new HashMap<>();
        for (Map.Entry<String, Object> entry : new Config(this
                .getDataFolder()
                .toPath()
                .resolve("emoji.yml")
                .toFile())
                .getAll().entrySet()) {
            emojiList.put(entry.getKey(), (String) entry.getValue());
        }
        //init API
        EmojiAPI.initAPI(emojiList);
        getServer().getCommandMap().register("", new EmojiCommand());
    }
}
