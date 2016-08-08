class FSM {
	private State[] states = { new A(), new B(), new C() };


}

abstract class State {
	public void on() { System.out.println}
}