package scripts;

import java.util.ArrayList;
import java.util.List;

import it.micronixnetwork.gaf.util.xml.XMLObject;
import it.micronixnetwork.pipe.Adapter;
import it.micronixnetwork.pipe.queue.Matrioska;

public class EmailAdapter extends Adapter {

    public EmailAdapter() {
	// TODO Auto-generated constructor stub
    }

    @Override
    public Matrioska convert(Matrioska input) {
	XMLObject data=(XMLObject)input.get("xmlobject");
	List<XMLObject> adddress=data.getChildren("email");
	
	List<String> toList=new ArrayList<String>();
	
	for (XMLObject xml : adddress) {
	    toList.add(xml.getText());
	}
	
	List<XMLObject> senders=data.getChildren("sender");
	
	if(senders.size()>0){
	    input.put(ticket,"sender",senders.get(0).getText());
	}
	
	input.remove(ticket, "xmlobject");
	
	input.put(ticket,"tolist",toList);
	
	return input;
    }
}
