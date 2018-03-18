public class CombatTest{
	ArrayList<Integer> neighbours = new ArrayList<Integer>(Arrays.asList(1,3,4));
	public class CT extends Country {
		public CT(int n){
			if(n == 1) {
				super(2, new ArrayList<Integer>(Arrays.asList(1,3,4)), "Canada", false);
				CT.setOwner(new Player("Bob"));
			}
			else if (n == 2) {
				super(1, new ArrayList<Integer>(Arrays.asList(2,3,4)), "USA", false);
				CT.setOwner(new Player("Dave"));
			}
		}
	}
	@Test
	public void test_SetAttackingCountry() {
		Combat com = new Combat(new CT(1), new CT(2));
		Country testCountry = Country(4, new ArrayList<Integer>(Arrays.asList(1,2,3)), "Russia", false);
		com.setAttackingCountry(testCountry);
		assertEquals( "Tried to set Attacking Country to Russia", testCountry,  com.getAttackingCountry());
	}
	@Test
	public void test_SetAttackingCountry() {
		Combat com = new Combat(new CT(1), new CT(2));
		Country testCountry = Country(4, new ArrayList<Integer>(Arrays.asList(1,2,3)), "Russia", false);
		com.setDefendingCountry(testCountry);
		assertEquals( "Tried to set Defending Country to Russia", testCountry,  com.getDefendingCountry());
	}
	@Test
	public void test_SetAttacker() {
		Combat com = new Combat(new CT(1), new CT(2));
		Player test = new Player("Jame");
		com.setAttacker(test);
		assertEquals( "Tried to set Player", test, com.getAttacker());
	}
	@Test
	public void test_SetDefender() {
		Combat com = new Combat(new CT(1), new CT(2));
		Player test = new Player("Jame");
		com.setDefender(test);
		assertEquals( "Tried to set Player", test, com.getDefender());
	}
	@Test
	public void test_SetNumAttackersPositive() {
		Combat com = new Combat(new CT(1), new CT(2));
		com.setNumAttackers(3);
		
		assertEquals( "Tried to set numAttackers to 3", 3, com.getNumAttackers());
	}
	@Test
	public void test_SetNumAttackersNegative() {
		Combat com = new Combat(new CT(1), new CT(2));
		com.setNumAttackers(-3);
		
		assertEquals( "Tried to set numAttackers to -3", 0, com.getNumAttackers());
	}
		
	public void test_SetNumDefendersPositive() {
		Combat com = new Combat(new CT(1), new CT(2));
		com.setNumDefenders(3);
		
		assertEquals( "Tried to set numDefenders to 3", 3, com.getNumAttackers());
	}
	@Test
	public void test_SetNumDefendersNegative() {
		Combat com = new Combat(new CT(1), new CT(2));
		com.setNumDefenders(-3);
		
		assertEquals( "Tried to set numDefenders to -3", 0, com.getNumAttackers());
	}

	@Test
	public void test_AttackerWins() {
		Combat com = new Combat(new CT(1), new CT(2));
		boolean boo = com.attackWin(3,1);
		assertEquals( "Attacking with 3, defender with 1", true, boo);
		
		
	}
	@Test
	public void test_AttackerLoses() {
		Combat com = new Combat(new CT(1), new CT(2));
		boolean boo = com.attackWin(1,3);
		assertEquals( "Attacking with 1, defender with 3", false, boo);
		
		
	}
	

	
	
	//	@Test
//	public void test_() {
//		assertEquals( "", "", );
//	}
	
}