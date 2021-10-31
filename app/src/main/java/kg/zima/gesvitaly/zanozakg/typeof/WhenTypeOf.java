package kg.zima.gesvitaly.zanozakg.typeof;

public class WhenTypeOf<S> {

	private S object;

	WhenTypeOf(S object) {
		this.object = object;
	}

	public <T> FirstIs<S, T> is(Class<T> type) {
		return new FirstIs<>(new Then<>(object), object, type);
	}
}
