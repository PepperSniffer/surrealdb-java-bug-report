import com.surrealdb.RecordId;
import com.surrealdb.Surreal;
import com.surrealdb.signin.RootCredential;

class Main {

    public static class MyDoc {
        public RecordId id;
        public String name;
        public String tenant;
    }

    public static void main(String[] args) {
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
                System.out.println("Doc ID: " + doc.id);
                System.out.println("Name: " + doc.name);
                System.out.println("Tenant: " + doc.tenant);

                var id = doc.id.getId();
                System.out.println(id.isArray()); // true
                // works fine till this point

                var t = id.getArray().len(); // Crash
            }
        }
    }
}
