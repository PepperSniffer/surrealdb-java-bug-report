import com.surrealdb.RecordId;

void main() {
	// it works when a Surreal instance is running
	// OR a previous Surreal instance has been created
	// I expect to be able to init a POJO without a Surreal instance running
	var recId = new RecordId("myRec", "foo"); //fails when no Surreal instance is running

	IO.println("Record ID: " + recId);
}