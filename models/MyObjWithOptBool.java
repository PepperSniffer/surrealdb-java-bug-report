package models;

import java.util.Optional;

public class MyObjWithOptBool {
	public Optional<Boolean> success;

	public MyObjWithOptBool(Optional<Boolean> success) {
		this.success = success;
	}

	public MyObjWithOptBool() {}
}
