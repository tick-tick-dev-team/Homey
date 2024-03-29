/*selectHome에서 사용*/
window.onload = function(){
	checkbirth();
}


async function checkbirth() {

	//날짜 (https://gent.tistory.com/413)
	var today = new Date();

	
	var month = ('0' + (today.getMonth() + 1)).slice(-2);
	var day = ('0' + today.getDate()).slice(-2);

	var dateString = month  + '-' + day;
	
	const userId = document.getElementById('for_birth').value;
	
	fetch('/users/' + userId + '/birth', {
		method : 'POST',
		body : userId
	})
	.then(response => response.text())
	.then(function(text){
		if(text == dateString){
			swal("생일을 진심으로 축하해주세요!", "이 집 주인의 생일은 " + text + "로 오늘입니다.", "info");
		    changeCursor();
			fireworks();
			}
		}
	);
}

function changeCursor() {
	const body = document.getElementById('selectHome');
	body.style.cssText='cursor:url(/img/cursor.cur), auto;';
}




function fireworks(){
	
	let canvas = document.getElementById('canvas');

	(function () {
	    // globals
	    var canvas;
	    var ctx;
	    var W;
	    var H;
	    var mp = 150; //max particles
	    var particles = [];
	    var angle = 0;
	    var tiltAngle = 0;
	    var confettiActive = true;
	    var animationComplete = true;
	    var deactivationTimerHandler;
	    var reactivationTimerHandler;
	    var animationHandler;

	    // objects

	    var particleColors = {
	        colorOptions: ["DodgerBlue", "OliveDrab", "Gold", "pink", "SlateBlue", "lightblue", "Violet", "PaleGreen", "SteelBlue", "SandyBrown", "Chocolate", "Crimson"],
	        colorIndex: 0,
	        colorIncrementer: 0,
	        colorThreshold: 10,
	        getColor: function () {
	            if (this.colorIncrementer >= 10) {
	                this.colorIncrementer = 0;
	                this.colorIndex++;
	                if (this.colorIndex >= this.colorOptions.length) {
	                    this.colorIndex = 0;
	                }
	            }
	            this.colorIncrementer++;
	            return this.colorOptions[this.colorIndex];
	        }
	    }

	    function confettiParticle(color) {
	        this.x = Math.random() * W; // x-coordinate
	        this.y = (Math.random() * H) - H; //y-coordinate
	        this.r = RandomFromTo(10, 15); //radius;
	        this.d = (Math.random() * mp) + 10; //density;
	        this.color = color;
	        this.tilt = Math.floor(Math.random() * 10) - 10;
	        this.tiltAngleIncremental = (Math.random() * 0.07) + .05;
	        this.tiltAngle = 0;

	        this.draw = function () {
	            ctx.beginPath();
	            ctx.lineWidth = this.r / 2;
	            ctx.strokeStyle = this.color;
	            ctx.moveTo(this.x + this.tilt + (this.r / 4), this.y);
	            ctx.lineTo(this.x + this.tilt, this.y + this.tilt + (this.r / 4));
	            return ctx.stroke();
	        }
	    }

	    $(document).ready(function () {
	        SetGlobals();

	        RestartConfetti();
	        setTimeout(function DeactivateConfetti(){
		        confettiActive = false;
		        ClearTimers();
		    },6000);
	        
	        
	       
	        
	        $(window).resize(function () {
	            W = window.innerWidth;
	            H = window.innerHeight;
	            canvas.width = W;
	            canvas.height = H;
	        });

	    });
	    

	    function SetGlobals() {
	        canvas = document.getElementById("canvas");
	        ctx = canvas.getContext("2d");
	        W = window.innerWidth;
	        H = window.innerHeight;
	        canvas.width = W;
	        canvas.height = H;
	    }

	    function InitializeConfetti() {
	        particles = [];
	        animationComplete = false;
	        for (var i = 0; i < mp; i++) {
	            var particleColor = particleColors.getColor();
	            particles.push(new confettiParticle(particleColor));
	        }
	        StartConfetti();
	    }

	    function Draw() {
	        ctx.clearRect(0, 0, W, H);
	        var results = [];
	        for (var i = 0; i < mp; i++) {
	            (function (j) {
	                results.push(particles[j].draw());
	            })(i);
	        }
	        Update();

	        return results;
	    }

	    function RandomFromTo(from, to) {
	        return Math.floor(Math.random() * (to - from + 1) + from);
	    }


	    function Update() {
	        var remainingFlakes = 0;
	        var particle;
	        angle += 0.01;
	        tiltAngle += 0.1;

	        for (var i = 0; i < mp; i++) {
	            particle = particles[i];
	            if (animationComplete) return;

	            if (!confettiActive && particle.y < -15) {
	                particle.y = H + 100;
	                continue;
	            }

	            stepParticle(particle, i);

	            if (particle.y <= H) {
	                remainingFlakes++;
	            }
	            CheckForReposition(particle, i);
	        }

	        if (remainingFlakes === 0) {
	            StopConfetti();
	        }
	    }

	    function CheckForReposition(particle, index) {
	        if ((particle.x > W + 20 || particle.x < -20 || particle.y > H) && confettiActive) {
	            if (index % 5 > 0 || index % 2 == 0) //66.67% of the flakes
	            {
	                repositionParticle(particle, Math.random() * W, -10, Math.floor(Math.random() * 10) - 20);
	            } else {
	                if (Math.sin(angle) > 0) {
	                    //Enter from the left
	                    repositionParticle(particle, -20, Math.random() * H, Math.floor(Math.random() * 10) - 20);
	                } else {
	                    //Enter from the right
	                    repositionParticle(particle, W + 20, Math.random() * H, Math.floor(Math.random() * 10) - 20);
	                }
	            }
	        }
	    }
	    function stepParticle(particle, particleIndex) {
	        particle.tiltAngle += particle.tiltAngleIncremental;
	        particle.y += (Math.cos(angle + particle.d) + 3 + particle.r / 2) / 3;
	        particle.x += Math.sin(angle);
	        particle.tilt = (Math.sin(particle.tiltAngle - (particleIndex / 3))) * 15;
	    }

	    function repositionParticle(particle, xCoordinate, yCoordinate, tilt) {
	        particle.x = xCoordinate;
	        particle.y = yCoordinate;
	        particle.tilt = tilt;
	    }

	    function StartConfetti() {
	        W = window.innerWidth;
	        H = window.innerHeight;
	        canvas.width = W;
	        canvas.height = H;
	        (function animloop() {
	            if (animationComplete) return null;
	            animationHandler = requestAnimFrame(animloop);
	            return Draw();
	        })();
	    }

	    function ClearTimers() {
	        clearTimeout(reactivationTimerHandler);
	        clearTimeout(animationHandler);
	    }

	    function DeactivateConfetti() {
	        confettiActive = false;
	        ClearTimers();
	    }

	    function StopConfetti() {
	        animationComplete = true;
	        if (ctx == undefined) return;
	        ctx.clearRect(0, 0, W, H);
	    }

	    function RestartConfetti() { //InitializeConfetti
	        ClearTimers();
	        StopConfetti();
	        reactivationTimerHandler = setTimeout(function () {
	            confettiActive = true;
	            animationComplete = false;
	            InitializeConfetti();
	        }, 10);

	    }

	    window.requestAnimFrame = (function () {
	        return window.requestAnimationFrame || 
	        window.webkitRequestAnimationFrame || 
	        window.mozRequestAnimationFrame || 
	        window.oRequestAnimationFrame || 
	        window.msRequestAnimationFrame || 
	        function (callback) {
	            return window.setTimeout(callback, 1000 / 60);
	        };
	    })();
	})();

	
	
}