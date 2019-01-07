package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import spring.AlreadyExistingMemberException;
import spring.ChangePasswordService;
import spring.IdPasswordNotMatchingException;
import spring.MemberListPrinter;
import spring.MemberNotFoundException;
import spring.MemberRegisterService;
import spring.RegisterRequest;


public class MainForSpring {
	
	private static ApplicationContext ctx = null;
	
	public static void main(String[] args) throws IOException
	{
		ctx = new GenericXmlApplicationContext("classpath:appCtx.xml");
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		while(true)
		{
			System.out.println("명령어를 입력하세요");
			String command = reader.readLine();
			if(command.equalsIgnoreCase("exit"))
			{
				System.out.println("종료합니다");
				break;
			}
			if(command.startsWith("new"))
			{
				processNewCommand(command.split(" "));
				continue;
			}
			else if(command.startsWith("change"))
			{
				processChangeCommand(command.split(" "));
				continue;
			}
			else if(command.equals("list"))
			{
				processListCommand();
				continue;
			}
			printHelp();
		}
	}

	private static void processListCommand() {
		// TODO Auto-generated method stub
		MemberListPrinter listPrinter = ctx.getBean("listPrinter",MemberListPrinter.class);
		listPrinter.printAll();
	}

	private static void processNewCommand(String[] arg) 
	{
		// TODO Auto-generated method stub
		if(arg.length != 5)
		{
			printHelp();
			return;	
		}
		MemberRegisterService regSvc = ctx.getBean("memberRegSvc", MemberRegisterService.class); //스프링 방식으로 객체 생성과 의존주입
		RegisterRequest req = new RegisterRequest();
		req.setEmail(arg[1]);
		req.setName(arg[2]);
		req.setPassword(arg[3]);
		req.setConfirmPassword(arg[4]);
		
		if(!req.isPasswordEqualToConfirmPassword())
		{
			System.out.println("암호와 확인 암호가 일치하지 않습니다");
			return;
		}
		try 
		{
			regSvc.regist(req);
			System.out.println("등록되었습니다.\n");
		}catch(AlreadyExistingMemberException e)
		{
			System.out.println("이미 존재하는 이메일입니다\n");
		}
	}
	
	private static void processChangeCommand(String[] arg)
	{
		if(arg.length != 4)
		{
			printHelp();
			return;	
		}
		
		ChangePasswordService changePwdSvc = ctx.getBean("changePwdSvc", ChangePasswordService.class);
		
		try {
			changePwdSvc.changePassword(arg[1], arg[2], arg[3]);
			System.out.println("암호를 변경했습니다 \n");
		}catch(MemberNotFoundException e)
		{
			System.out.println("존재하지 않는 이메일입니다. \n");
		}catch(IdPasswordNotMatchingException e)
		{
			System.out.println("이메일과 암호가 일치하지 않습니다. \n");
		}
		
	}
	
	private static void printHelp() {
		// TODO Auto-generated method stub
		System.out.println("잘못된 명령어. 사용법을 확인해주세요\n");
		System.out.println("명령어 사용법 : \n");
		System.out.println("new 이메일 이름 암호 암호 다시입력 \n");
		System.out.println("Change 이메일 현제 비밀번호 변경할 비밀번호 \n");
		System.out.println();
	}
	
	
		
	
}
