import java.util.Scanner;

public class MainGame {
    public static void main(String[] args) {
        Game game = new Game();
        Scanner scanner = new Scanner(System.in);

        // 플레이어 생성
        Game.player player = game.new player();

        int normalDamage = player.normalAttack(); //일반 공격
        int normalPosture = player.normalAttack_Posture();//일반 공격 강인도 피해
        int postureBreak = player.postureBreakDMG(player.atk, player.posture); //약점 격파 피해
        
        int skillDamage = player.skill(); //스킬 공격
        int skillPosture = player.skill_Posture(); //스킬 공격 강인도 피해

        int ultDamage = player.ultSkill(); //필살기 공격
        int ultPosture = player.ultskill_Posture();//필살기 강인도 피해

        int DIEDIEDIE = player.DIEDIEDIE(); //게임 로직 테스트용 적 즉사기. 게임에는 넣지 말것

        //스테이지 생성
        NormalStage normalStage = new NormalStage();
        normalStage.stage(); // 스테이지 생성

        int player_HP = player.hp;

        int enemy1_Hp = normalStage.stage[0].hp; //적 1의 체력
        int enemy1_Act = normalStage.stage[0].attackCount; //적 1의 행동 게이지
        int enemy1_Posture = normalStage.stage[0].posture; //적 1의 강인도

        int enemy2_Hp = normalStage.stage[1].hp; //적 2의 체력
        int enemy2_Act = normalStage.stage[1].attackCount; //적 2의 행동 게이지
        int enemy2_Posture = normalStage.stage[1].posture; //적 1의 강인도


        
        System.out.println("깃허브에 나도 이제 새로운 개발사항 올린다 야호 으하하하");
        System.out.println("시험공부 해야하는데 증말로다가 하기싫다 하하 종강해라 하하!");

        
        
        System.out.println("고블린 동굴 토벌-20234031 이승호");
        System.out.println("게임 시작");
     
        Game.stageON = true; // 스테이지 진입 표시 정적변수 수정
        
        System.out.println("고블린 무리가 출현했다!");

        player.ultPt = 0;
        player.skillpt = 3;
         
        while (Game.stageON == true) { 
   
            while (Game.stageON == true) {
            	
            	//승리 조건: 모든 적 처치
                if (normalStage.stage[0] == null && normalStage.stage[1] == null) {
                    Game.stageON = false;
                    Game.win = true;
                    break;
                }
              //패배 조건: 사망
                if (player_HP <= 0) {
                    Game.stageON = false;
                    break;
                }

       
            	System.out.println("플레이어의 차례 : 적을 공격하세요.");
            	if (normalStage.stage[0] != null) {
            	    System.out.print("현재 적의 체력: 졸병 고블린 1:" + enemy1_Hp );
            	    System.out.println(" // 현재 적의 강인도: 졸병 고블린 1:" + enemy1_Posture);
            	}
            	if (normalStage.stage[1] != null) {
            	    System.out.print("현재 적의 체력: 졸병 고블린 2:" + enemy2_Hp);
            	    System.out.println(" // 현재 적의 강인도: 졸병 고블린 2:" + enemy2_Posture);
            	}
            	System.out.println();
            	System.out.println("1.일반 공격 / 2.스킬 공격 / 3.필살기 발동 / 0.뒤로 돌아가기");	
            	System.out.println("남은 스킬 포인트:" + player.skillpt + "필살기 게이지:" + player.ultPt);
            	System.out.println();

                int attackSelect = scanner.nextInt();
                switch (attackSelect) {
                    case 1: //일반 공격
                        if (player.skillpt < 5) {
                            player.skillpt += 1; //스킬 포인트 1개 회복
                        }
                        System.out.println("공격할 적을 선택하세요");
                        int attackaim = scanner.nextInt(); //목표 선택
                        switch (attackaim) {
                            case 1: //1번째 적에게 일반 공격
                                enemy1_Hp -= normalDamage;
                                enemy1_Posture -= normalPosture;
                                player.normalAttack_UltPTUP();
                                
                                System.out.println("졸병 고블린 1에게 가로 베기! 남은 체력:" + enemy1_Hp + "남은 강인도:" + enemy1_Posture);
                                if (enemy1_Posture <= 0) {
                                    enemy1_Hp -= postureBreak; //약점 격파
                                    enemy1_Posture = normalStage.stage[0].posture; //데미지를 받고 강인도 다시 회복
                                }
                                if (enemy1_Hp <= 0) {
                                    normalStage.removeEnemy(0); //적 사망
                                }
                                break;

                            case 2: //2번째 적에게 일반 공격
                                enemy2_Hp -= normalDamage;
                                enemy2_Posture -= normalPosture;
                                player.normalAttack_UltPTUP();
                                
                                System.out.println("졸병 고블린 2에게 가로 베기! 남은 체력:" + enemy2_Hp + "남은 강인도:" + enemy2_Posture);
                                if (enemy2_Posture <= 0) {
                                    enemy2_Hp -= postureBreak; //약점 격파
                                    enemy2_Posture = normalStage.stage[1].posture;
                                }
                                if (enemy2_Hp <= 0) {
                                    normalStage.removeEnemy(1);
                                }
                                break;

                            case 0: //뒤로가기
                                continue;

                            default:
                                System.out.println("올바르지 않은 입력입니다. 다시 입력하세요");
                                break;
                        }
                        break;

                    case 2: //스킬 공격
                        {
                            if (player.skillpt > 0) {
                                player.skillpt -= 1; //스킬 포인트 1개 소모
                            } else {
                                System.out.println("스킬 포인트가 부족합니다");
                                continue;
                            }


                            System.out.println("공격할 적을 선택하세요");
                            attackaim = scanner.nextInt(); //목표 선택
                            switch (attackaim) {
                                case 1: //1번째 적에게 스킬 공격
                                    enemy1_Hp -= skillDamage;
                                    enemy1_Posture -= skillPosture;
                                    player.skill_UltPTUp();
                                    
                                    System.out.println("졸병 고블린 1에게 올려베기! 남은 체력:" + enemy1_Hp + "남은 강인도:" + enemy1_Posture);
                                    if (enemy1_Posture <= 0) {
                                        enemy1_Hp -= postureBreak; //약점 격파
                                        enemy1_Posture = normalStage.stage[0].posture; //데미지를 받고 강인도 다시 회복
                                    }
                                    if (enemy1_Hp <= 0) {
                                        normalStage.removeEnemy(0);
                                    }
                                    break; // 해당 case 블록 종료

                                case 2: //2번째 적에게 일반 공격
                                    enemy2_Hp -= skillDamage;
                                    enemy2_Posture -= skillPosture;
                                    player.skill_UltPTUp();
                                    
                                    System.out.println("졸병 고블린 2에게 올려베기! 남은 체력:" + enemy2_Hp + "남은 강인도:" + enemy2_Posture);

                                    if (enemy2_Posture <= 0) {
                                        enemy2_Hp -= postureBreak; //약점 격파
                                        enemy2_Posture = normalStage.stage[1].posture;
                                    }

                                    if (enemy2_Hp <= 0) {
                                        normalStage.removeEnemy(1);
                                    }
                                    break; // 해당 case 블록 종료

                                case 0: //뒤로가기
                                    continue;

                                default:
                                    System.out.println("올바르지 않은 입력입니다. 다시 입력하세요");
                                    break;
                            }
                            break; // switch 블록 종료
                        }

                    case 3: //필살기
                        {
                            if (player.ultPt< 100) {
                                System.out.println("필살기 게이지가 부족합니다!");
                                continue;
                            }

                            System.out.println("공격할 적을 선택하세요");
                            attackaim = scanner.nextInt(); //목표 선택
                            switch (attackaim) {
                                case 1: //1번째 적에게 필살기 사용
                                    enemy1_Hp -= ultDamage;
                                    enemy1_Posture -= ultPosture;
                                    System.out.println("졸병 고블린 1에게 별 부수기! 남은 체력:" + enemy1_Hp + "남은 강인도:" + enemy1_Posture);
                                  
                                    if (enemy1_Posture <= 0) {
                                        enemy1_Hp -= postureBreak; //약점 격파
                                        enemy1_Posture = normalStage.stage[0].posture; //데미지를 받고 강인도 다시 회복
                                    }
                                    
                                    if (enemy1_Hp <= 0) {
                                        normalStage.removeEnemy(0);
                                    }
                                    break; // 해당 case 블록 종료

                                case 2: //2번째 적에게 필살기 사용
                                    enemy2_Hp -= ultDamage;
                                    enemy2_Posture -= ultPosture;
                                    System.out.println("졸병 고블린 2에게 별 부수기! 남은 체력:" + enemy2_Hp + "남은 강인도:" + enemy2_Posture);

                                    if (enemy2_Posture <= 0) {
                                        enemy2_Hp -= postureBreak; //약점 격파
                                        enemy2_Posture = normalStage.stage[1].posture;
                                    }

                                    if (enemy2_Hp <= 0) {
                                        normalStage.removeEnemy(1);
                                    }
                                    break; // 해당 case 블록 종료

                                case 0: //뒤로가기
                                    continue;

                                default:
                                    System.out.println("올바르지 않은 입력입니다. 다시 입력하세요");
                                    break;
                            }
                            break; // switch 블록 종료
                        }

                    case 999: //로직 검사용 즉사기. 만약 진짜 게임을 만들어 배포한다면 이 코드는 삭제하는게 맞겠지?
                        System.out.println("공?격할! 적12을# 선!택!하세~!요!");
                        attackaim = scanner.nextInt(); //목표 선택
                        switch (attackaim) {
                            case 1: //1번째 적에게 일반 공격
                                enemy1_Hp -= DIEDIEDIE;
                                enemy1_Posture -= normalPosture;
                                System.out.println("졸병 고블린 1에게 가@@@@ㄹㅗ로 베베ㅔ베베베베ㅣ기!! 남은 체력:" + enemy1_Hp + "남은 강인도:" + enemy1_Posture);
                                if (enemy1_Posture <= 0) {
                                    enemy1_Hp -= postureBreak; //약점 격파
                                    enemy1_Posture = normalStage.stage[0].posture; //데미지를 받고 강인도 다시 회복
                                }
                                if (enemy1_Hp <= 0) {
                                    normalStage.removeEnemy(0); //적 사망
                                }
                                break;

                            case 2: //2번째 적에게 일반 공격
                                enemy2_Hp -= DIEDIEDIE;
                                enemy2_Posture -= normalPosture;
                                System.out.println("졸병 고블린 2에게ㅇㅇ2^&$ 가!!@#@$4기! 남은 체력:" + enemy2_Hp + "남은 강인도:" + enemy2_Posture);
                                if (enemy2_Posture <= 0) {
                                    enemy2_Hp -= postureBreak; //약점 격파
                                    enemy2_Posture = normalStage.stage[1].posture;
                                }
                                if (enemy2_Hp <= 0) {
                                    normalStage.removeEnemy(1);
                                }
                                break;


                        }
                    case 0: //뒤로가기
                        continue;


                    default: {
                        System.out.println("올바르지 않은 입력입니다. 다시 입력하세요");
                        continue;
                    }
                }

                //적의 차례 - enemy turn
                System.out.println("젹의 턴");

              

                if (normalStage.stage[0] != null) { //적이 사망하지 않았을 경우
                	
                	enemy1_Act -= 1;
               	 	System.out.println("졸병 고블린 1의 공격까지:" + enemy1_Act);
                	
               	 	if(enemy1_Act == 0) { ////고블린 1의 행동 게이지가 0이면 공격
                		player_HP -= normalStage.stage[0].attack();
                        System.out.println("고블린 1의 공격을 받았다! 남은 HP:" + player_HP);
                        enemy1_Act = normalStage.stage[0].attackCount;
                	}
                    
                }
                if (normalStage.stage[1] != null ) { //사망하지 않았을 경우   
                	  enemy2_Act -= 1;
                      System.out.println("졸병 고블린 2의 공격까지:" + enemy2_Act);
                      
                    if(enemy2_Act == 0){ //고블린 2의 행동 게이지가 0이면 공격
                    	  player_HP -= normalStage.stage[1].attack();
                          System.out.println("고블린 2의 공격을 받았다! 남은 HP:" + player_HP);
                          enemy2_Act = normalStage.stage[1].attackCount;
                    }
                  
                }
              

                System.out.println();
                
              //승리 조건: 모든 적 처치
                if (normalStage.stage[0] == null && normalStage.stage[1] == null) {
                    Game.stageON = false;
                    Game.win = true;
                    break;
                }
              //패배 조건: 사망
                if (player_HP <= 0) {
                    Game.stageON = false;
                    break;
                }
            	
            } //내부 루프 마지막

            if (Game.stageON == false) { // 게임 종료 조건 확인
                break; // 외부 루프 종료
            }
        }
        
        if (Game.win == true) {
            System.out.println("게임 종료. 플레이어 승리!");
        } else {
            System.out.println("게임 오버. 플레이어 패배.");  
        }
        scanner.close();
        return;
        
    }
}
