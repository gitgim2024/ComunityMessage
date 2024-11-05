const MainHeaderMenuComponent = {
	template: `
		<Transition name="menuTransition">
			<div class="headerMenu-wrap" v-if="headerMenuCheck[0]" @click="closeMenu()">
				<div class="headerMenu-containers" @click.stop="">
					<div class="headerMenu-container" id="headerMenu-alarm" v-if="headerMenuCheck[1]">
						<div class="header-contents flexCenter" @click="toggleExpand(0)" :style="{backgroundColor: notificationIndex === 0 && notifications[0].length !== 0 ? '#efefef' : '#FCFCFC'}">
							<p class="header-alarm-content">메시지</p>
							<div class="header-noti-menu-counts" v-if="notiCounts[0] !== 0">{{ notiCounts[0] }}</div>
						</div>
						<Transition name="menuTransition"><div v-if="notificationIndex === 0 && notifications[0].length !== 0" class="header-expanded-content"></div></Transition>
						
						<div class="header-contents flexCenter" @click="toggleExpand(1)" :style="{backgroundColor: notificationIndex === 1 && notifications[1].length !== 0 ? '#efefef' : '#FCFCFC'}">
							<p class="header-alarm-content">좋아요</p>
							<div class="header-noti-menu-counts" v-if="notiCounts[1] !== 0">{{ notiCounts[1] }}</div>
						</div>
						<Transition name="menuTransition">
							<div v-if="notificationIndex === 1 && notifications[1].length !== 0" class="header-expanded-content">
								<div class="header-notification flexCenter" v-for="(notification, j) in notifications[1]" :key="j" @click="redirectIframeNoti(1,1,j,'updateLikeNoti?ln='+notification.like_noti_id)">
									<div class="header-notification-content">
										<span style="font-weight: 400;">{{ notifications[1][j].target_user_id }}</span>님이 당신의 {{ notifications[1][j].like_noti_type }}에 <span style="font-weight: 400;">좋아요</span>를 눌렀습니다.<br>
										<div style="width: inherit; font-size: 9px; text-align: right;">
											{{
												(nowTime.getTime() - new Date(notification.like_noti_date).getTime()) < 60 * 1000 ? Math.floor((nowTime.getTime() - new Date(notification.like_noti_date).getTime()) / (1000))+'초전' :
												(nowTime.getTime() - new Date(notification.like_noti_date).getTime()) < 60 * 60 * 1000 ? Math.floor((nowTime.getTime() - new Date(notification.like_noti_date).getTime()) / (60 * 1000))+'분전' :
												(nowTime.getTime() - new Date(notification.like_noti_date).getTime()) < 24 * 60 * 60 * 1000 ? Math.floor((nowTime.getTime() - new Date(notification.like_noti_date).getTime()) / (60 * 60 * 1000))+'시간전' :
												Math.floor((nowTime.getTime() - new Date(notification.like_noti_date).getTime()) / (24 * 60 * 60 * 1000))+'일전'
											}}
											{{ notification.like_noti_check === 1 ? ' | 읽음' : ' | 읽지않음' }}
										</div>
									</div>
									<div class="hearder-delete-noti flexCenter" @click.stop="fetchNoti('deleteLikeNoti?ln='+notification.like_noti_id)"><div>x</div></div>
								</div>
							</div>
						</Transition>
						
						<div class="header-contents flexCenter" @click="toggleExpand(2)" :style="{backgroundColor: notificationIndex === 2 && notifications[2].length !== 0 ? '#efefef' : '#FCFCFC'}">
							<p class="header-alarm-content">댓글</p>
							<div class="header-noti-menu-counts" v-if="notiCounts[2] !== 0">{{ notiCounts[2] }}</div>
						</div>
						<Transition name="menuTransition">
							<div v-if="notificationIndex === 2 && notifications[2].length !== 0" class="header-expanded-content">
								<div class="header-notification flexCenter" v-for="(notification, j) in notifications[2]" :key="j" @click="redirectIframeNoti(1,2,j,'updateCommentsNoti?cn='+notification.comments_noti_id)">
									<div class="header-notification-content">
										<span style="font-weight: 400;">{{ notifications[2][j].target_user_id }}</span>님이 당신의 {{ notifications[2][j].feed_id !== 0 ? '피드' : '게시글' }}에 <span style="font-weight: 400;">댓글</span>을 달았습니다.<br>
										<div style="width: inherit; font-size: 9px; text-align: right;">
											<div style="float: left; width: 150px; overflow: hidden; white-space: nowrap; text-overflow: ellipsis; font-size: 10px;">{{ notification.post_comments_text !== null ? '\"'+notification.post_comments_text+'\"' : '\"'+notification.feed_comments_text+'\"' }}&nbsp</div>
											{{
												(nowTime.getTime() - new Date(notification.comments_noti_date).getTime()) < 60 * 1000 ? " | "+Math.floor((nowTime.getTime() - new Date(notification.comments_noti_date).getTime()) / (1000))+'초전' :
												(nowTime.getTime() - new Date(notification.comments_noti_date).getTime()) < 60 * 60 * 1000 ? " | "+Math.floor((nowTime.getTime() - new Date(notification.comments_noti_date).getTime()) / (60 * 1000))+'분전' :
												(nowTime.getTime() - new Date(notification.comments_noti_date).getTime()) < 24 * 60 * 60 * 1000 ? " | "+Math.floor((nowTime.getTime() - new Date(notification.comments_noti_date).getTime()) / (60 * 60 * 1000))+'시간전' :
												" | "+Math.floor((nowTime.getTime() - new Date(notification.comments_noti_date).getTime()) / (24 * 60 * 60 * 1000))+'일전'
											}}
											{{ notification.comments_noti_check === 1 ? ' | 읽음' : ' | 읽지않음' }}
										</div>
									</div>
									<div class="hearder-delete-noti flexCenter" @click.stop="fetchNoti('deleteCommentsNoti?cn='+notification.comments_noti_id)"><div>x</div></div>
								</div>
							</div>
						</Transition>
						
						<div class="header-contents flexCenter" @click="toggleExpand(3)" :style="{backgroundColor: notificationIndex === 3 && notifications[3].length !== 0 ? '#efefef' : '#FCFCFC'}">
							<p class="header-alarm-content">팔로우</p>
							<div class="header-noti-menu-counts" v-if="notiCounts[3] !== 0">{{ notiCounts[3] }}</div>
						</div>
						<Transition name="menuTransition">
							<div v-if="notificationIndex === 3 && notifications[3].length !== 0" class="header-expanded-content">
								<div class="header-notification" v-for="(notification, j) in notifications[3]" :key="j" @click="redirectIframeNoti(1,3,j,'updateFollowNoti?fn='+notification.follow_noti_id)">
									<div class="header-notification-content" v-if="notification.follow_noti === 0">
										<span style="font-weight: 400;">{{ notifications[3][j].target_user_id }}</span>님이 당신을 팔로우 했습니다.<br>
										<div style="width: inherit; font-size: 9px; text-align: right;">
											{{
												(nowTime.getTime() - new Date(notification.follow_noti_date).getTime()) < 60 * 1000 ? Math.floor((nowTime.getTime() - new Date(notification.follow_noti_date).getTime()) / (1000))+'초전' :
												(nowTime.getTime() - new Date(notification.follow_noti_date).getTime()) < 60 * 60 * 1000 ? Math.floor((nowTime.getTime() - new Date(notification.follow_noti_date).getTime()) / (60 * 1000))+'분전' :
												(nowTime.getTime() - new Date(notification.follow_noti_date).getTime()) < 24 * 60 * 60 * 1000 ? Math.floor((nowTime.getTime() - new Date(notification.follow_noti_date).getTime()) / (60 * 60 * 1000))+'시간전' :
												Math.floor((nowTime.getTime() - new Date(notification.follow_noti_date).getTime()) / (24 * 60 * 60 * 1000))+'일전'
											}}
											{{ notification.follow_noti_check === 1 ? ' | 읽음' : ' | 읽지않음' }}
										</div>
									</div>
									<div class="hearder-delete-noti flexCenter" v-if="notification.follow_noti === 0" @click.stop="fetchNoti('deleteFollowNoti?ui='+notification.user_id+'&ti='+notification.target_user_id)"><div>x</div></div>
									<div class="header-notification-content" v-if="notification.follow_noti === 1" style="margin-bottom: 1.5px;">
										<span style="font-weight: 400;">{{ notifications[3][j].target_user_id }}</span>님이 당신에게 팔로우를 요청했습니다. 
										<div style="display: inline-block; width: inherit; font-size: 9px; text-align: right;">
											{{
												(nowTime.getTime() - new Date(notification.follow_noti_date).getTime()) < 60 * 1000 ? Math.floor((nowTime.getTime() - new Date(notification.follow_noti_date).getTime()) / (1000))+'초전' :
												(nowTime.getTime() - new Date(notification.follow_noti_date).getTime()) < 60 * 60 * 1000 ? Math.floor((nowTime.getTime() - new Date(notification.follow_noti_date).getTime()) / (60 * 1000))+'분전' :
												(nowTime.getTime() - new Date(notification.follow_noti_date).getTime()) < 24 * 60 * 60 * 1000 ? Math.floor((nowTime.getTime() - new Date(notification.follow_noti_date).getTime()) / (60 * 60 * 1000))+'시간전' :
												Math.floor((nowTime.getTime() - new Date(notification.follow_noti_date).getTime()) / (24 * 60 * 60 * 1000))+'일전'
											}}
										</div>
									</div>
									<div class="hearder-follow-noti" v-if="notification.follow_noti === 1">
										<div></div>
										<div class="header-follow-select" @click.stop="fetchNoti('privateFollowNoti?ui='+notification.user_id+'&ti='+notification.target_user_id)">수락</div>
										<div class="header-follow-select" @click.stop="fetchNoti('deleteFollowNoti?ui='+notification.user_id+'&ti='+notification.target_user_id)">거절</div>
										<div></div>
									</div>
									<div class="header-notification-content" v-if="notification.follow_noti === 2">
										<span style="font-weight: 400;">{{ notifications[3][j].target_user_id }}</span>님이 당신의 팔로우를 수락했습니다.<br>
										<div style="width: inherit; font-size: 9px; text-align: right;">
											{{
												(nowTime.getTime() - new Date(notification.follow_noti_date).getTime()) < 60 * 1000 ? Math.floor((nowTime.getTime() - new Date(notification.follow_noti_date).getTime()) / (1000))+'초전' :
												(nowTime.getTime() - new Date(notification.follow_noti_date).getTime()) < 60 * 60 * 1000 ? Math.floor((nowTime.getTime() - new Date(notification.follow_noti_date).getTime()) / (60 * 1000))+'분전' :
												(nowTime.getTime() - new Date(notification.follow_noti_date).getTime()) < 24 * 60 * 60 * 1000 ? Math.floor((nowTime.getTime() - new Date(notification.follow_noti_date).getTime()) / (60 * 60 * 1000))+'시간전' :
												Math.floor((nowTime.getTime() - new Date(notification.follow_noti_date).getTime()) / (24 * 60 * 60 * 1000))+'일전'
											}}
											{{ notification.follow_noti_check === 1 ? ' | 읽음' : ' | 읽지않음' }}
										</div>
									</div>
									<div class="hearder-delete-noti flexCenter" v-if="notification.follow_noti === 2" @click.stop="fetchNoti('deleteFollowNoti?ui='+notification.user_id+'&ti='+notification.target_user_id)"><div>x</div></div>
								</div>
							</div>
						</Transition>
					</div>
					<div class="headerMenu-container" id="headerMenu-profile" v-if="headerMenuCheck[2]">
						<div class="header-contents flexCenter" style="height: 60px; background-color: #f9f9f9; pointer-events: none;"><p class="header-nick-content">{{ userNickname }}님</p></div>
						<div class="header-contents flexCenter" @click="redirectIframe(0,4,'')"><p class="header-profile-content">마이페이지</p></div>
						<div class="header-contents flexCenter" @click="redirectIframe(2,5,'')"><p class="header-profile-content">설정</p></div>
						<div class="header-contents flexCenter" @click="logoutWhale()" style="border: none; border-bottom-left-radius: 18px; border-bottom-right-radius: 18px;"><p class="header-profile-content">로그아웃</p></div>
					</div>
				</div>
	    	</div>
    	</Transition>
	`,
	props: {
		headerMenuCheck: Array,
		userNickname: String,
		notifications: Array,
		notiCounts: Array,
		getNotification: {type: Function, default() {return 'Default function'}},
	},
	data() {
		return {
			notificationIndex: null,
			nowTime: new Date(),
		};
	},
	mounted() {
	},
	methods: {
		closeMenu() {this.$emit('header-close-menu'); this.notificationIndex = null;},
		redirectIframe(i,j,k) {this.$emit('menu-redirect-iframe',i,j,k); this.notificationIndex = null;},
		redirectIframeNoti(i,j,k,l) {
			let x;
			let y;
			if (this.notifications[j][k].post_id > 0) {x = 6; y = `?c=${this.notifications[j][k].community_id}&p=${this.notifications[j][k].post_id}`;}
			else if (this.notifications[j][k].feed_id > 0) {x = 7; y = `?f=${this.notifications[j][k].feed_id}`;}
			else if (this.notifications[j][k].follow_noti_id > 0) {x = 8; y = `?u=${this.notifications[j][k].target_user_id}`;}
			this.$emit('menu-redirect-iframe',i,x,y);
			this.notificationIndex = null;
			fetch('main/'+l);
		},
		logoutWhale() {
			// 스프링 클라이언트 정보 초기화
			localStorage.clear();
			// 스프링 서버 정보 초기화
			location.href='/whale/main/logout';
		},
		toggleExpand(i) {this.notificationIndex = this.notificationIndex === i ? null : i;},
		fetchNoti(i) {fetch('main/'+i); setTimeout(() => {this.getNotification();}, 500);},
	},
};

export default MainHeaderMenuComponent;