
public class Game {
    // 랜덤 객체 생성
    static boolean stageON = false; //스테이지 진입 확인
    static boolean win = false; //승리 판정 변수
 
    static class stat {
        // 캐릭터 기본 옵션들
        int hp; // 체력
        int atk; // 공격력
        int skillpt = 3; // 스킬 포인트. 스킬 공격을 사용할 때 필요하다
        int ultPt = 0; // 필살기 포인트. 100이 될 경우 필살기를 사용 가능하다.
       
        int attackCount; //공격 카운트. 적에게만 존재하며 카운트가 0이 될 경우 플레이어에게 공격을 가한다.
        int posture; //강인도수치. 적에게만 존재하며 0이 될경우 "약점 격파"가 발동되어 공격 카운트가 1 증가되고 추가 데미지를 입는다.
        

        stat(int hp, int atk, int attackCount, int posture) { // 부모 클래스 생성자
            this.hp = hp;
            this.atk = atk;
            this.ultPt = 0;
            this.attackCount = attackCount;
            this.posture = posture;
        }

        //기본 데미지 공식 메서드
        int attackDMG(int atk, int skill) {
            // 기본 데미지 공식 = 기본 공격력 * 스킬 계수
            int atkDamage = atk * skill / 100;
            return atkDamage;
        }
      //약점 격파 데미지 공식 메서드
        int postureBreakDMG(int playerAtk , int posture) {
        	//약점 격파 데미지 공식 = 기본 공격력 * 200% + 적의 강인도 수치 / 100
        	int postureDMG = playerAtk * 200 + posture/100;
        	return postureDMG;
        }

    }

    class player extends stat {
        player() {
            super(40, 20, 0, 0); // 체력, 공격력, 행동 게이지, 강인도
        }
        
        void normalAttack_UltPTUP() { //일반 공격의 필살기 게이지 회복
        	ultPt = ultPt + 20;
        	if(ultPt>100) ultPt = 100;
        }
        
        int normalAttack() { //일반 공격
        	
            return attackDMG(atk, 70); // 일반 공격, 공격력의 70% 계수 데미지
        }
        
        int normalAttack_Posture() { //일반공격의 강인도 피해
        	return 2;
        }
        
        void skill_UltPTUp() {
        	ultPt = ultPt + 40;
        	if (ultPt > 100) {
                ultPt = 100; // 최대값 제한
            }
        }
        int skill() {
        	
            if (skillpt > 0) {
                skillpt--; // 스킬포인트 1개 사용하여 스킬공격
            	skill_UltPTUp();}
            else {
                skillpt = 0;}
               
            
            return attackDMG(atk, 150);
            // 스킬 공격, 공격력의 150% 계수 데미지
        }
        int skill_Posture() { //스킬공격의 강인도 피해
        	return 5;
        }
       

        int ultSkill() {
            if (ultPt == 100) { //ultPt를 소비하여 필살기 사용.
                ultPt = 0;
                return attackDMG(atk, 200);} // 필살기, 공격력의 200% 걔수 데미지}
            else
            	return 0; //필살기 포인트 부족
        }
        int ultskill_Posture() { //필살기의 강인도 피해
        	return 10;
        }
        
        //로직 점검을 위한 즉사기
        int DIEDIEDIE() {
                return attackDMG(atk, 9999999);} //테스트용, 즉사기
          
        }
        int DIEDIEDIE_Posture() { //즉사기
        	return 9999999;
        }
    }

						
    class enemy extends Game.stat { //Game.stat 이 아니라 그냥 stat을 인식 못하네?????????? 붙이니까 오류 해결됨
        enemy() {
            super(70, 40, 3, 10); // 체력, 공격력, 행동 게이지, 강인도
        }

        int attack() {
            return attackDMG(atk, 80); // 적의 공격, 공격력의 80%계수 데미지
        }
  
    }

    
 // 일반 스테이지 생성 클래스
    class NormalStage { 
        enemy[] stage;
        
        // 생성자 내부에서 스테이지 생성
        void stage() {
            int numEnemies = 2; 
            stage = new enemy[numEnemies]; // 난수에 따라 배열 크기 설정

            // 배열 요소 초기화
            for (int i = 0; i < numEnemies; i++) {
                stage[i] = new enemy();
            }
        }
        
        void removeEnemy(int index) { //적 사망시 배열에서 삭제
            System.out.println("졸병 고블린 " + (index + 1) + "이 쓰러졌다.");
            // 배열에서 삭제
            stage[index] = null;
        }
        
    }

 

    

