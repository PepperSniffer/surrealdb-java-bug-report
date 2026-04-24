import com.surrealdb.Surreal;
import models.MyDoc;

void main() {
	segFault();
}

private static void segFault() {
	try (Surreal surreal = new Surreal()) {
		// also tested with a running docker container : surrealdb:v3.0.4
		// surreal.connect("ws://127.0.0.1:8000");
		// surreal.signin(new RootCredential("root", "root"));

		surreal.connect("memory");

		surreal.useNs("my_ns").useDb("my_db");

		surreal.query(
				"""
						CREATE myDoc:["myTenant", rand::ulid()] CONTENT {name: "foo", tenant: "myTenant"}
						"""
		);

		var docs = surreal.select(MyDoc.class, "myDoc");

		while (docs.hasNext()) {
			var doc = docs.next();
			IO.println("Doc ID: " + doc.id);
			IO.println("Name: " + doc.name);
			IO.println("Tenant: " + doc.tenant);

			var id = doc.id.getId();
			IO.println(id.isArray()); // true
			// works fine till this point
			var arr = id.getArray();


			//crash all of these lines
			arr.len();
			arr.get(0);
			arr.iterator();
			arr.synchronizedIterator();
		}
	}
}
