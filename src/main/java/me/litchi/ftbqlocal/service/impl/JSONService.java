package me.litchi.ftbqlocal.service.impl;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import me.litchi.ftbqlocal.service.FtbQService;
import me.litchi.ftbqlocal.utils.HandlerCounter;
import net.minecraft.Util;
import net.minecraft.network.chat.*;

import java.util.ArrayList;
import java.util.List;

import static me.litchi.ftbqlocal.utils.HandlerCounter.*;

public class JSONService implements FtbQService {
    @Override
    public  String handleJSON(Component parsedText) {
        try{
            String jsonString;
            List<Component> flatList = parsedText.toFlatList(Style.EMPTY);
            StringBuilder jsonStringBuilder = new StringBuilder("[\"\",");
            for(Component c : flatList){
                HandlerCounter.addCounter();
                String text = c.getContents();
                Style style = c.getStyle();
                if(style != Style.EMPTY){
                    jsonStringBuilder.append("{");
                    TextColor color = style.getColor();
                    if(color != null){
                        jsonStringBuilder.append("\"color\":\"").append(color).append("\",");
                    }
                    if (style.isUnderlined()){
                        jsonStringBuilder.append("\"underlined\":true,");
                    }
                    if (style.isStrikethrough()){
                        jsonStringBuilder.append("\"strikethrough\":true,");
                    }
                    if (style.isBold()){
                        jsonStringBuilder.append("\"bold\":true,");
                    }
                    if (style.isItalic()){
                        jsonStringBuilder.append("\"italic\":true,");
                    }
                    if (style.isObfuscated()){
                        jsonStringBuilder.append("\"obfuscated\":true,");
                    }
                    String textKey = HandlerCounter.getPrefix() + ".rich_description" + HandlerCounter.getCounter();
                    HandlerCounter.transKeys.put(textKey, addPercent(text));
                    jsonStringBuilder.append("\"translate\":\"").append(textKey).append("\"");
                    ClickEvent clickEvent = style.getClickEvent();
                    if(clickEvent != null){
                        String clickEventValue = clickEvent.getValue();
                        String clickEventAction = clickEvent.getAction().getName();
                        jsonStringBuilder.append(",\"clickEvent\":{\"action\":\"")
                                .append(clickEventAction).append("\",\"value\":\"")
                                .append(clickEventValue).append("\"}");
                    }
                    HoverEvent hoverEvent = style.getHoverEvent();
                    if(hoverEvent != null){
                        String hoverEventAction = hoverEvent.getAction().getName();
                        JsonObject hoverEventJSON = hoverEvent.serialize();
                        JsonObject hoverValue = hoverEventJSON.get("contents").getAsJsonObject();
                        String hoverText = hoverValue.get("text").getAsString();
                        HandlerCounter.addCounter();
                        String hoverKey = HandlerCounter.getPrefix() + ".rich_description" + HandlerCounter.getCounter();
                        jsonStringBuilder.append(",\"hoverEvent\":{\"action\":\"")
                                .append(hoverEventAction).append("\",\"contents\":{\"translate\":\"")
                                .append(hoverKey).append("\"}}");
                        HandlerCounter.transKeys.put(hoverKey, addPercent(hoverText));
                    }
                    jsonStringBuilder.append("},");
                } else {
                    String textKey = HandlerCounter.getPrefix() + ".rich_description" + HandlerCounter.getCounter();
                    HandlerCounter.transKeys.put(textKey, addPercent(text));
                    jsonStringBuilder.append("{\"translate\":\"").append(textKey).append("\"},");
                }
            }
            if(jsonStringBuilder.charAt(jsonStringBuilder.length()-1) == ','){
                jsonStringBuilder.deleteCharAt(jsonStringBuilder.length()-1);
            }
            jsonStringBuilder.append("]");
            jsonString = jsonStringBuilder.toString();
            return jsonString;
        }catch(Exception e){
            HandlerCounter.log.info(e.getMessage());
            return "";
        }
    }

}
