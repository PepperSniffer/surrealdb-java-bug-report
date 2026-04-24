import com.surrealdb.Surreal;

void main() {
	//works
	try (var surreal = new Surreal()) {
		surreal.connect("memory");

		surreal.useNs("my_ns").useDb("my_db");
		surreal.create("record", new MyRecordClass("foo", "myTenant"));

		var iterable = surreal.select(MyRecordClass.class, "record");

		IO.println("Using a class:");
		while (iterable.hasNext()){
			var rec = iterable.next();
			IO.println("Name: " + rec.getName());
			IO.println("Tenant: " + rec.getTenant());
		}
	}

	// fails
	try (var surreal = new Surreal()) {
		surreal.connect("memory");

		surreal.useNs("my_ns").useDb("my_db");
		surreal.create("record", new MyRecordRecord("foo", "myTenant"));

		var iterable = surreal.select(MyRecordRecord.class, "record");

		//fails because Surreal doesn't support record deserialization
		IO.println("Using a record:");
		while (iterable.hasNext()){
			var rec = iterable.next();
			IO.println("Name: " + rec.name());
			IO.println("Tenant: " + rec.tenant());
		}
	}
}

public record MyRecordRecord(String name, String tenant){
}

public static class MyRecordClass {
	private String name;
	private String tenant;

	public MyRecordClass(String name, String tenant) {
		this.name = name;
		this.tenant = tenant;
	}

	public MyRecordClass() {

	}

	public String getName() {
		return name;
	}

	public String getTenant() {
		return tenant;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTenant(String tenant) {
		this.tenant = tenant;
	}
}
