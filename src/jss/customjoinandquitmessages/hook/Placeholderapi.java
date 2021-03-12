package jss.customjoinandquitmessages.hook;


import org.bukkit.entity.Player;

import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;
import jss.customjoinandquitmessages.utils.EventUtils;
import jss.customjoinandquitmessages.utils.Utils;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class Placeholderapi {
	
	private CustomJoinAndQuitMessages plugin;
	private EventUtils eventUtils = new EventUtils(plugin);
	
	public Placeholderapi(CustomJoinAndQuitMessages plugin) {
		this.plugin = plugin;
	}

	public void onPlaceHolderAPI() {
		if(getPlaceHolderAPI()) {
			Utils.sendColorMessage(eventUtils.getConsoleSender(), Utils.getPrefix() + "&5 <||============================================----");
			Utils.sendColorMessage(eventUtils.getConsoleSender(), Utils.getPrefix() + "&5 <|| &c* &ePlaceHolderAPI:&b" + " " + plugin.placeholders);
			Utils.sendColorMessage(eventUtils.getConsoleSender(), Utils.getPrefix() + "&5 <|| &c* &eVars PlaceHolderAPI:&a true");
			Utils.sendColorMessage(eventUtils.getConsoleSender(), Utils.getPrefix() + "&5 <|| &c* &bCustomJoinAndQuitMessages:&a true");
			Utils.sendColorMessage(eventUtils.getConsoleSender(), Utils.getPrefix() + "&5 <||============================================----");
			//new CustomJoinAndQuitMessagesExpand(plugin).register();
		}else {
			Utils.sendColorMessage(eventUtils.getConsoleSender(), Utils.getPrefix() + "&5 <||============================================----");
			Utils.sendColorMessage(eventUtils.getConsoleSender(), Utils.getPrefix() + "&5 <|| &c* &ePlaceHolderAPI:&b" + " " + plugin.placeholders);
			Utils.sendColorMessage(eventUtils.getConsoleSender(), Utils.getPrefix() + "&5 <|| &c* &eVars PlaceHolderAPI:&c false");
			Utils.sendColorMessage(eventUtils.getConsoleSender(), Utils.getPrefix() + "&5 <|| &c* &bCustomJoinAndQuitMessages:&c false");
			Utils.sendColorMessage(eventUtils.getConsoleSender(), Utils.getPrefix() + "&5 <||============================================----");
		}
	}
	
	public boolean getPlaceHolderAPI() {
		if(plugin.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
			plugin.placeholders = true;
		}
		return plugin.placeholders;
	}
	
	public class CustomJoinAndQuitMessagesExpand extends PlaceholderExpansion{

	    private CustomJoinAndQuitMessages plugin;
	 
	    public CustomJoinAndQuitMessagesExpand(CustomJoinAndQuitMessages plugin) {
	    	this.plugin = plugin;
	    }	 

	    public boolean persist(){
	        return true;
	    }

	    public boolean canRegister(){
	        return true;
	    }

	    public String getAuthor(){
	        return "jonagamerpro1234";
	    }

	    public String getIdentifier(){
	        return "cji";
	    }
	 
	    public String getVersion(){
	        return plugin.getDescription().getVersion();
	    }

	    public String onPlaceholderRequest(Player player, String identifier){
	 
	        if(player == null){
	            return null;
	        }

	        if(identifier.equals("name")){
	        	return player.getName();
	        }
	        if(identifier.equals("displayname")) {
	        	return player.getDisplayName();
	        }
	 
	        return null;
	    }
	}
	
	 

}
