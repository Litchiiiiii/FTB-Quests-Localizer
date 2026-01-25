package me.litchi.ftbqlocal;

import com.google.gson.*;
import me.litchi.ftbqlocal.events.ModEvents;
import net.minecraftforge.common.MinecraftForge;

import net.minecraftforge.fml.common.Mod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Mod("ftbquestlocalizer")
public class FtbQuestLocalizerMod {
	public static final Logger log = LoggerFactory.getLogger(FtbQuestLocalizerMod.class);
	public static final Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

	public FtbQuestLocalizerMod(){
		MinecraftForge.EVENT_BUS.addListener(ModEvents::onCommandsRegister);
	}

}

