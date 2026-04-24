import com.surrealdb.Surreal;
import models.MyObjWithOptBool;

void main() {
	try (var surreal = new Surreal()) {
		surreal.connect("memory");
		surreal.useNs("my_ns").useDb("my_db");

//		surreal.create("myBools", new models.MyObjWithBool(false)); // works
//		surreal.select(models.MyObjWithBool.class, "myBools").forEachRemaining(obj -> { // fails because of boolean deser
//			IO.println("Success: " + obj.success);
//		});

//		surreal.create("myDoc", new models.MyObjWithBool(null)); // fails because of null Boolean

		//so we need to use a Optinoal
//		surreal.create("myDoc", new models.MyObjWithOptBool(Optional.empty())); //still fails

		// so we need to use a null Optional
		surreal.create("myDoc", new MyObjWithOptBool(null)); //works

		var iter = surreal.select(MyObjWithOptBool.class, "myDoc");
		while (iter.hasNext()) {
			var obj = iter.next();
			//but at deserialization time, we have to handle the case where the Optional is null,
			// because Surreal doesn't handle empty Optionals properly
			boolean success = obj.success == null ? false : obj.success.orElse(false);

			IO.println("Success: " + success);
		}
	}
}