package grupo4.dds.persistor;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;

public class MongoPersistor {

	private Morphia morphia;
	private Datastore dataStore;
	private static MongoPersistor self = new MongoPersistor();

	private MongoPersistor() {
		morphia = new Morphia();
		dataStore = morphia.createDatastore(new MongoClient(), "dds_grupo4");	
	}

	public static MongoPersistor get() {
		return self;
	}

	public Datastore dataStore() {
		return dataStore;
	}

}