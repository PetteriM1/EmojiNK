package cn.daogecmd.emojink.command;

import cn.daogecmd.emojink.api.EmojiAPI;
import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.form.window.FormWindowSimple;

import java.util.List;
import java.util.stream.Collectors;

public class EmojiCommand extends Command {
    public EmojiCommand() {
        super("emoji", "Send emoji");
        this.setAliases(new String[]{"emj", "ej"});
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        //send form
        List<ElementButton> buttons = EmojiAPI.getAPI()
                .getEmojiList()
                .entrySet()
                .stream()
                .map(entry -> {
                    String text = "§9" + entry.getValue() +
                            "§8\n" +
                            entry.getKey();
                    return new ElementButton(text);
                }).collect(Collectors.toList());
        FormWindowSimple form = new FormWindowSimple("Emoji", "§bChoose the emoji you want", buttons);
        form.addHandler((player, formID) -> {
            FormResponseSimple response = form.getResponse();
            if (response == null) {
                return;
            }
            String emojiId = response.getClickedButton().getText().split("\n")[1];
            EmojiAPI.getAPI().sendEmoji(player, emojiId);
        });
        ((Player) sender).showFormWindow(form);
        return true;
    }
}
