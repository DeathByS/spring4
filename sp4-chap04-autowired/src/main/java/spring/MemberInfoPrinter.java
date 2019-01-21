package spring;

import org.springframework.beans.factory.annotation.Autowired;
//설정 메소드 방식으로 의존객체를 주입받는 소스.
public class MemberInfoPrinter 
{

	@Autowired
	private MemberDao memDao;
	private MemberPrinter printer;
	
	
	/*
	 설정 매서드.
	 매서드 이름이 set으로 시작됨
	 set 뒤에는 프로퍼티이름의  첫글자를 대문자로 치환해서 사용 
	 한개의 피라미터, 피라미터 타입 = 프로퍼티 타입
	 */
	public void setMemberDao(MemberDao memberDao)
	{
		this.memDao = memberDao;
	}
	
	@Autowired
	public void setPrinter(MemberPrinter printer)
	{
		this.printer = printer;
	}
	
	public void printMemberInfo(String email)
	{
		Member member = memDao.selectByEmail(email);
		if(member == null)
		{
			System.out.println("데이터가 없습니다");
			return;
		}
		
		printer.print(member);
		System.out.println();
	}
	
}
