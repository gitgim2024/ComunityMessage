const MainFooterComponent = {
	template: `
		<div>
			<Transition name="menuTransition">
				<div class="playerPlaylistContainer" v-if="isPlayered">
					<div class="playlistBody">
						<div class="playlist-header flexCenter">재생 목록</div>
						<div class="playlist-delete flexCenter" @click="isPlayered = false;">x</div>
						<div class="playlist-contents" v-for="(item, i) in resPlaylists" :key="i" @mouseover="isShow[i] = true" @mouseleave="isShow[i] = false" :style="{backgroundColor: item.id === trackInfo[4] ? '#E2E2E2' : '#ffffff'}">
							<div class="playlist-font flexCenter" v-if="!isShow[i]">{{ i+1 }}</div>
							<div class="flexCenter" v-if="addIsShow(i)">
				                <svg
				                    data-encore-id="icon"
				                    role="img"
				                    aria-hidden="true"
				                    viewBox="0 0 24 24"
				                    class="playlistTrackBtn"
				                    style="height: 16px; cursor: pointer;"
				                    @click="playPlayer(item.uri)"
				                >
				                    <path d="m7.05 3.606 13.49 7.788a.7.7 0 0 1 0 1.212L7.05 20.394A.7.7 0 0 1 6 19.788V4.212a.7.7 0 0 1 1.05-.606z"
				                    ></path>
				                </svg>
				            </div>
							<div style="display: flex;">
								<div><img :src="item.album.images[0].url" :alt="item.name" height="55px" style="border-radius: 5px; cursor: pointer;" @click="sendStreaming({ type: 'albumDetail', albumId: item.album.id },'?type=albumDetail&albumId='+item.album.id)"></div>
								<div>
									<p class="playlist-font" style="margin: 6px 0 0 10px; font-weight: 300; cursor: pointer;" @click="sendStreaming({ type: 'trackDetail', trackId: item.id },'?type=trackDetail&trackId='+item.id)">{{ item.name }}</p>
									<p class="playlist-font" style="margin: 2px 0 0 10px; font-size: 13px; cursor: pointer;" @click="sendStreaming({ type: 'artistDetail', artistId: item.artists[0].id },'?type=artistDetail&artistId='+item.artists[0].id)">{{ item.artists[0].name }}</p>
								</div>
							</div>
						</div>
					</div>
				</div>
			</Transition>
			<div class="footer flexCenter">
		        <div class="player">
		        	<div class="playerComponent" id="playerLeft">
		        		<div class="playerInfo flexCenter" @click="sendStreaming({ type: 'albumDetail', albumId: trackInfo[6] },'?type=albumDetail&albumId='+trackInfo[6])"><img :src="trackInfo[0]" alt="" height="48px" style="border-radius: 5px; opacity: 0.9;"></div>
		        		<div class="playerRightStyle"><p class="playerTrackName playerInfo" @click="sendStreaming({ type: 'trackDetail', trackId: trackInfo[4] },'?type=trackDetail&trackId='+trackInfo[4])">{{ trackInfo[1] }}</p><p class="playerArtistName playerInfo" style="margin-top: 3px;" @click="sendStreaming({ type: 'artistDetail', artistId: trackInfo[7] },'?type=artistDetail&artistId='+trackInfo[7])">{{ trackInfo[2] }}</p></div>
		        		<div class="playerRightStyle" @click="insertTrackLike()"><img class="playerImg" :src="isLiked[ trackInfo[5] ? 1 : 0]" alt="Music Whale Like Button" width="23px" height="23px"></div>
		        	</div>
		        	<div class="playerComponent flexCenter">
		        	</div>
		            <div class="playerComponent flexCenter">
		            	<button class="playerBtn flexCenter" @click="shufflePlay()"><img class="playerImg" src="static/images/streaming/player/shuffle.png" alt="Music Whale Shuffle Button" height="32px" :style="{backgroundColor: isShuffled ? '#F5F5F5' : '#FCFCFC'}"></button>
		                <button class="playerBtn flexCenter" @click="prevPlay()"><img src="static/images/streaming/player/prev.png" alt="Music Whale Previous Button" height="42px"></button>
		                <button class="playerBtn flexCenter" @click="togglePlay()"><img :src="playBtnSrc[playBtnSrcIndex]" alt="Music Whale Play Button" height="42px"></button>
		                <button class="playerBtn flexCenter" @click="nextPlay()"><img src="static/images/streaming/player/next.png" alt="Music Whale Next Button" height="42px"></button>
		                <button class="playerBtn flexCenter" @click="repeatPlay()" style="position: relative;"><img class="playerImg" :src="repeatBtnSrc[repeatBtnSrcIndex]" alt="Music Whale Repeat Button" height="32px" :style="{backgroundColor: isRepeated ? '#F5F5F5' : '#FCFCFC'}"></button>
		            </div>
		            <div class="playerComponent"></div>
		            <div class="playerComponent" id="playerRight">
		            	<div class="playerRightMargin"><img class="playerPlayListImg playerInfo" src="static/images/streaming/player/playlist.png" alt="Music Whale Playlist Button" width="34px" height="34px" @click="getPlaylist()"></div>
		            </div>
		        </div>
		    </div>
		    <div class="footerMargin"></div>
	    </div>
	`,
	props: {
		fetchIframe: {type: Function, default() {return 'Default function'}},
		fetchWebApi: {type: Function, default() {return 'Default function'}},
		startPage: Array,
		trackInfo: Array,
	},
	data() {
		return {
			player: null,
			playBtnSrc: ['static/images/streaming/player/play.png','static/images/streaming/player/pause.png'],
			playBtnSrcIndex: 0,
			repeatBtnSrc: ['static/images/streaming/player/repeat.png','static/images/streaming/player/repeatOnce.png'],
			repeatBtnSrcIndex: 0,
			isRepeated: false,
			isShuffled: false,
			isLiked: ['static/images/streaming/player/like.png','static/images/streaming/player/likeFill.png'],
			sliderValue: 0,
			volumeValue: 50,
			tempVolume: null,
			timer: null,
			playTime: [],
			isHovered: [false,false],
			isPlayered: false,
			playlists: null,
			resPlaylists: [],
			isShow: [],
		};
	},
	mounted() {
		this.playerOn();
	},
	methods: {
		playerOn() {
			window.onSpotifyWebPlaybackSDKReady = () => {
				this.player = new Spotify.Player({
			        name: 'Whale Player',
			        getOAuthToken: cb => { cb(sessionStorage.accessToken); },
			        volume: 0.5
			    });
			    
			    // [ Connection ]
			    this.player.connect().then(success => {
			        if (success) {
			            console.log('The Web Playback SDK successfully connected to Spotify!');
			        }
			    });
			    
			    // [ Ready ]
			    this.player.addListener('ready', ({ device_id }) => {
					sessionStorage.device_id = device_id;
					
					// [ The Spring Web App ]
					(async () => {await this.fetchData(`http://localhost:9002/whale/main/device_id`);})();
					
					// [ The Node js Web App ]
					if (this.startPage[0] === 'streaming') {this.fetchIframe('leftIframe',sessionStorage.device_id);}
					if (this.startPage[1] === 'streaming') {this.fetchIframe('rightIframe',sessionStorage.device_id);}
					
					(async () => {try {await this.fetchWebApi(`v1/me/player`,'PUT',{ device_ids: [ sessionStorage.device_id ]});} catch(error) {}})();
					
			        console.log('Ready with Device ID', device_id);
			    });
			    
			    // [ Not Ready ]
			    this.player.addListener('not_ready', ({ device_id }) => {
			        console.log('Device ID has gone offline', device_id);
			    });
			
			    this.player.addListener('initialization_error', ({ message }) => {
			        console.error(message);
			    });
			
			    this.player.addListener('authentication_error', ({ message }) => {
			        console.error(message);
			    });
			
			    this.player.addListener('account_error', ({ message }) => {
			        console.error(message);
			    });
			    
			    // [ Player State Changed ]
			    this.player.addListener('player_state_changed', (state) => {
			        this.trackInfo[0] = state.track_window.current_track.album.images[0].url;
			        this.trackInfo[1] = state.track_window.current_track.name;
			        this.trackInfo[2] = state.track_window.current_track.artists[0].name;
			        this.trackInfo[3] = state.track_window.current_track.album.name;
			        this.trackInfo[4] = state.track_window.current_track.id;
			        
			        (async () => {
						try {
							const result = await this.fetchWebApi(`v1/tracks/${this.trackInfo[4]}`,'GET');
							this.trackInfo[6] = await result.album.id;
							this.trackInfo[7] = await result.artists[0].id;
						} catch(error) {
						}
					})();
			        
					// [ 재생 중이라면 버튼 이미지 정지 버튼 변환 ]
					if (!state.paused && this.playBtnSrcIndex === 0) {this.playBtnSrcIndex = 1;}
					// [ 일회 반복 중이라면 버튼 이미지 변환 ]
					if (state.repeat_mode === 2) {this.repeatBtnSrcIndex = 1; this.isRepeated = true;}
					else if (state.repeat_mode === 1) {this.repeatBtnSrcIndex = 0; this.isRepeated = true;}
					else {this.repeatBtnSrcIndex = 0; this.isRepeated = false;}
					// [ 셔플 중이라면 버튼 배경 이미지 변환 ]
					if (state.shuffle_mode === 1) {this.isShuffled = true;}
					else {this.isShuffled = false;}
					
					// 현재 재생바 정보 저장
					this.sliderValue = (state.position / state.duration) * 100;
					this.playTime[0] = this.returnTime(state.position);
					this.playTime[1] = this.returnTime(state.duration);
					this.playTime[2] = state.position;
					this.playTime[3] = state.duration;
					
					if(this.timer) {clearInterval(this.timer);}
					if(!state.paused) {
						this.timer = setInterval(
							() => {
								this.playTime[2] += 1000;
								this.sliderValue = (this.playTime[2] / this.playTime[3]) * 100;
								this.playTime[0] = this.returnTime(this.playTime[2]);
							}, 1000);
					} else {
						clearInterval(this.timer);
					}
					
					// 현재 재생 중인 음악 서버에 전송하여 저장
					fetch(`/whale/streaming/currentTrackInfo`, {
						headers: {
							'Accept': 'application/json',
				            'Content-Type': 'application/json'
				        },
				        method: 'POST',
				        body: JSON.stringify({
							artistName: this.trackInfo[2],
							trackName: this.trackInfo[1],
							albumName: this.trackInfo[3],
							trackCover: this.trackInfo[0],
							trackSpotifyId: this.trackInfo[4]
						})
					})
					.then(response => response.json())
						.then(data => {
							if (data.result === 'yes') {this.trackInfo[5] = true;}
							else {this.trackInfo[5] = false;}
					});
			    });
			}
		},
		
		async insertTrackLike() {
			let x;
			
			if (this.trackInfo[5] === false) {x = 'insertTrackLike';}
			else {x = 'deleteTrackLike';}
			
            try {
                const body = {
                    artistName: this.trackInfo[2],
					trackName: this.trackInfo[1],
					albumName: this.trackInfo[3],
					trackCover: this.trackInfo[0],
					trackSpotifyId: this.trackInfo[4]
                };
                const response = await fetch(`streaming/${ x }`, {
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json'
                    },
                    method: 'POST',
                    body: JSON.stringify(body)
                });
                
                if (response.ok) {
					console.log("Success updating Track Like Data to the Spring Wep App");
		        } else {
		            console.error('Failed to update the  Track Like Data: ', response.statusText);
		        }
            } catch (error) {
                console.error('Error while fetching the  Track Like Data:', error);
            }
            
            this.trackInfo[5] = !this.trackInfo[5];
        },
        
        async insertTrackCnt() {
            try {
                const body = {
                    artistName: this.trackInfo[2],
					trackName: this.trackInfo[1],
					albumName: this.trackInfo[3],
					trackCover: this.trackInfo[0],
					trackSpotifyId: this.trackInfo[4]
                };
                const response = await fetch(`streaming/insertTrackCnt`, {
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json'
                    },
                    method: 'POST',
                    body: JSON.stringify(body)
                });
                
                if (response.ok) {
					console.log("Success updating Track Cnt Data to the Spring Wep App");
		        } else {
		            console.error('Failed to update the  Track Cnt Data: ', response.statusText);
		        }
            } catch (error) {
                console.error('Error while fetching the  Track Cnt Data:', error);
            }
        },
        async fetchData(address) {
			const body = {
				device_id: sessionStorage.device_id,
			};
	        const response = await fetch(`${ address }`, {
		        headers: {
		            'Accept': 'application/json',
		            'Content-Type': 'application/json'
		        },
		        method: 'POST',
		        body: JSON.stringify(body)
	    	});
	        
	        if (response.ok) {
	            console.log("Success fetching Data to the Spring Wep App");
	        } else {
	            console.error('Failed to fetch the device_id: ', response.statusText);
	        }
		},
		
		// 플레이어 기능
		async shufflePlay() {
			if (this.isShuffled) {try {await this.fetchWebApi(`v1/me/player/shuffle?state=${false}&device_id=${sessionStorage.device_id}`,'PUT');} catch(error) {}}
			else {try {await this.fetchWebApi(`v1/me/player/shuffle?state=${true}&device_id=${sessionStorage.device_id}`,'PUT');} catch(error) {}}
		},
		togglePlay() {
			if (this.playBtnSrcIndex === 1) {
				this.playBtnSrcIndex = 0;
			} else {
				this.playBtnSrcIndex = 1;
				this.insertTrackCnt();
			}
			this.player.togglePlay();
		},
		nextPlay() {
			this.player.getCurrentState().then(
				async (state) => {
					if (state.track_window.next_tracks[0] === undefined) {
						try {const data = await this.fetchWebApi(`v1/recommendations?limit=1&seed_tracks=${state.track_window.current_track.id}`,'GET'); await this.fetchWebApi(`v1/me/player/play?device_id=${sessionStorage.device_id}`,'PUT',{ "uris": [`${data.tracks[0].uri}`] }); this.insertTrackCnt();} catch(error) {}
					} else {
						this.player.nextTrack();
						this.insertTrackCnt();
						console.log("next");
					}
				}
			);
		},
		prevPlay() {this.player.previousTrack(); this.insertTrackCnt();},
		async repeatPlayFunction(state) {try {await this.fetchWebApi(`v1/me/player/repeat?state=${state}&device_id=${sessionStorage.device_id}`,'PUT');} catch(error) {}},
		async repeatPlay() {
			if (this.repeatBtnSrcIndex === 1 && this.isRepeated) {await this.repeatPlayFunction('off');}
			else if (this.repeatBtnSrcIndex === 0 && this.isRepeated) {await this.repeatPlayFunction('track');}
			else {await this.repeatPlayFunction('context');}
		},
		
		// 플레이어 정보 송신 기능
		sendStreaming(i,j) {
			let x = $("#leftIframe").get(0).src;
			let y = $("#rightIframe").get(0).src;
			if (x === 'http://localhost:9002/whale/streaming') {this.fetchIframe('leftIframe',i);}
			else if (y === 'http://localhost:9002/whale/streaming') {this.fetchIframe('rightIframe',i);}
			else {this.$emit('footer-music-toggle', 3, 0, j);}
		},
		
		// 플레이어 재생바 관련 기능
		updateSliderBackground() {
			this.player.seek( (this.sliderValue / 100) * this.playTime[3] )
		},
		
		updateVolumeBackground() {
			this.player.setVolume(this.volumeValue / 100);
		},
		
		returnTime(ms) {
			return `${ String(Math.floor( ms / (1000 * 60 ) )).padStart(2, "0") }:${ String(Math.floor( ( ms % (1000 * 60 )) / 1000 )).padStart(2, "0") }`;
		},
		
		muteVolume() {
			this.tempVolume = this.volumeValue;
			this.volumeValue = 0;
			this.player.setVolume(this.volumeValue / 100);
		},
		
		returnVolume() {
			this.volumeValue = this.tempVolume;
			this.player.setVolume(this.volumeValue / 100);
		},
		
		// 플레이어 재생목록
		async getPlaylist() {
			if (this.isPlayered === false) {
				try {
					this.resPlaylists = [];
					const result = await this.fetchWebApi(`v1/me/player/queue`,`GET`);
					if (await result) {
		                this.playlists = await result;
						const temp = [];
						this.playlists.queue.forEach(el => {
							if (!temp.includes(el.id) && el.id !== this.playlists.currently_playing.id) {
								temp.push(el.id);
								this.resPlaylists.push(el);
							}
						});
						this.resPlaylists.unshift(this.playlists.currently_playing);
		            } else {
		                console.error('Failed to fetch user playlist queue:', result.statusText);
		            }
				} catch(error) {
				}
			} else {
				this.playlists = null;
			}
			this.isPlayered = !this.isPlayered
		},
        addIsShow(i) {
            this.isShow.push(false);
            return this.isShow[i];
        },
        async playPlayer(i) {
			const body = {
				"uris" : this.resPlaylists.map(el => { return el.uri }),
				"offset": {"uri": `${ i }` }
			};
			fetch(`https://api.spotify.com/v1/me/player/play?device_id=${ sessionStorage.device_id }`, {
					headers: {
						Authorization: `Bearer ${sessionStorage.accessToken}`,
					},
					method: 'PUT',
					body: JSON.stringify(body)
				}
			);
        },
	},
};

export default MainFooterComponent;
