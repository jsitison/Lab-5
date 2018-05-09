package pkgEnum;

public enum eCardNo {

	FIRST(0), SECOND(1), THIRD(2),FOURTH(3), FIFTH(4), SIX(5), SEVEN(6), EIGHT(7), NINE(8), TEN(9);

	private int iCardNo;

	private eCardNo(int iCardNo) {
		this.iCardNo = iCardNo;
	}

	public int getiCardNo() {
		return iCardNo;
	}
}
