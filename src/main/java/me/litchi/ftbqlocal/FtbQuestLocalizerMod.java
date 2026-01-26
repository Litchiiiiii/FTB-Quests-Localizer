package me.litchi.ftbqlocal;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.litchi.ftbqlocal.events.ModEvents;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Mod("ftbquestlocalizer")
public class FtbQuestLocalizerMod{
	public static final Logger log = LoggerFactory.getLogger(FtbQuestLocalizerMod.class);
	public static final Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

	public FtbQuestLocalizerMod(){
		NeoForge.EVENT_BUS.addListener(ModEvents::onCommandsRegister);
		}

}

