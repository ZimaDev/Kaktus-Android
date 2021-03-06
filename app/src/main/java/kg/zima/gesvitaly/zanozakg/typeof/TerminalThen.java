package kg.zima.gesvitaly.zanozakg.typeof;

import com.annimon.stream.function.Consumer;

public class TerminalThen<S> extends Then<S> {

	public TerminalThen() {
		super(null);
	}

	@Override
	public <T> ThenIs<S, T> is(Class<T> type) {
		return new TerminalThenIs<>(this, null, null);
	}

	@Override
	public void orElse(Consumer<S> orElseBlock) {
		//no-op
	}
}
