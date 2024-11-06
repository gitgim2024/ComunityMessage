/**
 * 메세지 테이블
 */
$(document).ready(function() {
    const modal = document.getElementById("room-set");
    const closeModal = document.getElementById("closeModal");

    const dotImages = document.querySelectorAll(".dotImg");
    dotImages.forEach(dot => {
        dot.addEventListener("click", function(event) {
            // 모달 표시
            modal.style.display = "block";

            // 클릭된 dotImg의 위치 계산
            const rect = dot.getBoundingClientRect();
            const margin = 20; // 마진값

            // 모달 위치 설정 (마진을 고려)
            const modalWidth = modal.offsetWidth; // 모달 너비
            const modalHeight = modal.offsetHeight; // 모달 높이

            // X 위치 조정
            let leftPosition = rect.left + window.scrollX - margin;
            if (leftPosition + modalWidth > window.innerWidth) {
                leftPosition = window.innerWidth - modalWidth - margin; // 화면 오른쪽 벗어날 경우 조정
            }
            if (leftPosition < 0) {
                leftPosition = margin; // 화면 왼쪽 벗어날 경우 조정
            }

            // Y 위치 조정
            let topPosition = rect.top + window.scrollY + margin; // 클릭 위치 아래에 모달을 위치
            
            // 화면 아래쪽 벗어나는 경우 조정
            if (topPosition + modalHeight > window.innerHeight) {
                topPosition = window.innerHeight - modalHeight - margin; // 화면 아래쪽 벗어날 경우 조정
            }

            // 화면 위쪽 벗어나는 경우 조정
            if (topPosition < 0) {
                topPosition = margin; // 위쪽 조정
            }

            modal.style.top = topPosition + "px"; // Y 위치
            modal.style.left = leftPosition + "px"; // X 위치
        });
    });

    closeModal.addEventListener("click", function() {
        modal.style.display = "none"; // 모달 닫기
    });

    // 모달 외부 클릭 시 닫기
    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    };
	
});

function goToMessageRoom(roomId,userList) {
	   $.ajax({
	          url: 'messageRoom', // 서버로 보낼 URL
	          method: 'POST', // HTTP 요청 방식
	          data: {
	              roomId: roomId,
	              userList: userList
	          },
	          success: function(response) {
	              // 성공적으로 응답을 받았을 때의 처리
				  // 요청 성공 시 처리
				  $('.message-container').html(response); // 받아온 데이터를 #result에 삽입
	          },
	          error: function(jqXHR, textStatus, errorThrown) {
	              // 오류 발생 시 처리
	              console.error("에러 발생: ", textStatus, errorThrown);
	          }
	      });
   }
