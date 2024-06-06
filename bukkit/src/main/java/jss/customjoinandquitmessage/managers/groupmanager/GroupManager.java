package jss.customjoinandquitmessage.managers.groupmanager;

import jss.customjoinandquitmessage.managers.groupmanager.util.FirstJoin;
import jss.customjoinandquitmessage.managers.groupmanager.util.Join;
import jss.customjoinandquitmessage.managers.groupmanager.util.Quit;
import org.bukkit.configuration.ConfigurationSection;

public class GroupManager extends GroupHelper {

    //antes de retornar el objeto join debera verificar si existe el grupo para evitar errores
    //tambien debera tomar encuenta el grupo del jugador actual si esta usando json o DB, para
    // esto se tomara encuenta la cache y se evitara la carga constante de los datos desde un
    // archivo o DB


    //estos metodos pemitiran facilitar la modulacion del codigo y ayudando a que sea mas facil de modificar el codigo
    //para nuevas caracteristicas, ...etc

    public Join getJoin(){

        // añadir verificaciones
        return  new Join("default",config);
    }

    public Quit getQuit(){

        // añadir verificaciones
        return  new Quit("default",config);
    }

    public FirstJoin getFirstJoin(){

        // añadir verificaciones
        return  new FirstJoin("default",config);
    }

}
