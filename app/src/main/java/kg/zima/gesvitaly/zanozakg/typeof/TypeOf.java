package kg.zima.gesvitaly.zanozakg.typeof;

public class TypeOf {

	public static <S> WhenTypeOf<S> whenTypeOf(S object) {
		return new WhenTypeOf<>(object);
	}
}
