package tw.nekomimi.nekogram.helpers;

import org.json.JSONException;
import org.json.JSONObject;

import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.R;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.Utilities;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLRPC;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tw.nekomimi.nekogram.NekoConfig;

public class MessageHelper extends BaseController {

    private static final MessageHelper[] Instance = new MessageHelper[UserConfig.MAX_ACCOUNT_COUNT];

    public MessageHelper(int num) {
        super(num);
    }

    public static ArrayList<TLRPC.MessageEntity> checkBlockedUserEntities(MessageObject messageObject) {
        if (NekoConfig.ignoreBlocked.Bool() && MessagesController.getInstance(UserConfig.selectedAccount).blockePeers.indexOfKey(messageObject.getFromChatId()) >= 0) {
            ArrayList<TLRPC.MessageEntity> entities = new ArrayList<>(messageObject.messageOwner.entities);
            var spoiler = new TLRPC.TL_messageEntitySpoiler();
            spoiler.offset = 0;
            spoiler.length = messageObject.messageOwner.message.length();
            entities.add(spoiler);
            return entities;
        } else {
            return messageObject.messageOwner.entities;
        }
    }
}
