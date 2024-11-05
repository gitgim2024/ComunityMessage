const MainHeaderComponent = {
	template: `
		<div class="header">
	        <div class="headerItems flexCenter">
	        	<div id="adminRedirect" v-if="isAdmin === '1'" @click="redirectAdmin()"><img src="static/images/main/adminIcon.png" alt="Admin Img" width="45px" height="45px"></div>
	        </div>
	        <div class="headerItems flexCenter" id="headerLogo" @click="goMain()">
	            <img src="static/images/main/whaleLogo.png" alt="Music Whale Logo" height="80px">
	        </div>
	        <div class="headerItems" id="headerContents">
	            <div class="flexCenter" id="headerAlarm" :style="{backgroundColor: headerMenuCheck[1] ? '#efefef' : '#FCFCFC'}" @click="alarmCheck()">
	                <img v-if="notiCountsSum === 0" src="static/images/main/bellEmpty.png" alt="Alarm" height="33px" style="opacity: 0.5;">
	                <img v-if="notiCountsSum !== 0" src="static/images/main/bellNoti.png" alt="Alarm" height="33px" style="opacity: 0.5;">
	                <div class="flexCenter" id="header-noti-count" v-if="notiCountsSum !== 0"><p id="header-noti-count-font">{{ notiCountsSum }}</p></div>
	            </div>
	            <div class="flexCenter" id="headerProfile" :style="{ backgroundColor: headerMenuCheck[2] ? '#efefef' : '#FCFCFC' }" @click="profileCheck()">
	                <div class="flexCenter" id="profile">
	                	<img :src="userImageUrl" alt="Profile Image" width="100%" height="100%" style="border-radius: 50%; opacity: 0.8;" v-show="userImageUrl">
	                </div>
	            </div>
	            <div id="headerMargin"></div>
	        </div>
	    </div>
	`,
	props: {
		headerMenuCheck: Array,
		userImageUrl: String,
		notiCountsSum: Number,
	},
	data() {
		return {
			isAdmin: sessionStorage.access_id,
		};
	},
	mounted() {
	},
	methods: {
		goMain() {this.$emit('reset-main');},
		alarmCheck() {this.$emit('header-alarm-toggle', 1);},
		profileCheck() {this.$emit('header-profile-toggle', 2);},
		redirectAdmin() {location.href='/whale/admin/adminMainView';},
	},
};

export default MainHeaderComponent;