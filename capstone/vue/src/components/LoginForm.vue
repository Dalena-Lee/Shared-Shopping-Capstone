<template>

<div>
    <article>
      <div class="container" :class="{'sign-up-active' : signUp}">
        <div class="overlay-container">
          <div class="overlay">
            <div class="overlay-left">
              <h4>Welcome Back!</h4>
              <p>Have an account? Please login with your personal information.</p>
              <div
                class="alert alert-danger"
                role="alert"
                v-if="invalidCredentials"
                >
                <h5>Invalid username and password!</h5>
              </div>
              <button class="invert" id="signIn" @click="signUp = !signUp">Sign In</button>
            </div>
            <div class="overlay-right">
              <h4>Hello, Friend!</h4>
              <p>Enter your personal details to start shopping with friends!</p>
              <button class="invert" id="signUp" @click="signUp = !signUp">Sign Up</button>
            </div>
          </div>
        </div>
        <form class="sign-up" action="#" @submit.prevent="register">
          <h3>Create An Account</h3>
          <div class="alert alert-danger" role="alert" v-if="registrationErrors">
          <h5>{{ registrationErrorMsg }}</h5>
          </div>
          <label for="username" class="sr-only"></label>
          <input 
          type="text" 
          id="username" 
          class="form-control" 
          placeholder="Username" 
          v-model="registerUser.username"
          required
          autofocus/>
          <label for="password" class="sr-only"></label>
          <input 
          type="password" 
          id="password"
          class="form-control"
          placeholder="Password"
          v-model="registerUser.password"
          required />
          <input
          type="password"
          id="confirmPassword"
          class="form-control"
          placeholder="Confirm Password"
          v-model="registerUser.confirmPassword"
          required
          />          
          <button @click="signUp = !signUp" type="submit">Sign Up</button>
        </form>
        <form class="sign-in" action="#" @submit.prevent="login">
          <h4>Sign In</h4>
          <div
            class="alert alert-danger"
            role="alert"
            v-if="invalidCredentials"
          >
          <h5>Invalid username and password!</h5>
          </div>
          <div
            class="alert alert-success"
            role="alert"
            v-if="this.$route.query.registration"
            >
            <h5>Thank you for registering, please sign in.</h5>
          </div>
          <h5 v-else>Use your account</h5>
          <label for="login-username" class="sr-only"></label>
          <input
              type="text"
              id="login-username"
              class="form-control"
              placeholder="Username"
              v-model="loginUser.username"
              required
              autofocus
          />
          <label for="login-password" class="sr-only"></label>
          <input
              type="password"
              id="login-password"
              class="form-control"
              placeholder="Password"
              v-model="loginUser.password"
              required
          />  
          <button type="submit">Sign In</button>
        </form>
      </div>
    </article>
  </div>

</template>

<script>
import authService from "../services/AuthService";

export default {
  name: "login",
  components: {},
  data() {
    return {
      signUp: false,
      loginUser: {
        username: "",
        password: ""
      },
      registerUser: {
        username: '',
        password: '',
        confirmPassword: '',
        role: 'user',
      },
      registrationErrors: false,
      registrationErrorMsg: 'There were problems registering this user.',
      invalidCredentials: false
    };
  },
  methods: {
    register() {
      if (this.registerUser.password != this.registerUser.confirmPassword) {
        this.registrationErrors = true;
        this.registrationErrorMsg = 'Password & Confirm Password do not match.';
      } else {
        authService
          .register(this.registerUser)
          .then((response) => {
            if (response.status == 201) {
              this.$router.push({
                query: { registration: 'success' },
              });
            }
          })
          .catch((error) => {
            const response = error.response;
            this.registrationErrors = true;
            if (response.status === 400) {
              this.registrationErrorMsg = 'Bad Request: Validation Errors';
            }
          });
      }
    },
    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = 'There were problems registering this user.';
    },
    login() {
      authService
        .login(this.loginUser)
        .then(response => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            this.$router.push("/");
          }
        })
        .catch(error => {
          const response = error.response;

          if (response.status === 401) {
            this.invalidCredentials = true;
        }
      });
    },
    autoLogin(username, password){
      this.loginUser.username = username;
      this.loginUser.password = password;

      authService
        .login(this.loginUser)
        .then(response => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            this.$router.push("/");
        }
      })
    }
  }
};
</script>

<style scoped>
.alert {
  margin-top: 10px;
  text-align: center;
}
body {
overflow: hidden;
font-family: 'Montserrat', sans-serif;
}

.container {
position: relative;
width: 768px;
height: 420px;
border-radius: 10px;
overflow: hidden;
box-shadow: 0 15px 30px rgba(0, 0, 0, 0.2), 0 10px 10px rgba(0, 0, 0, 0.2);
background: white;
margin-left: 20px;
margin-top: 20px;
}

.container .overlay-container {
position: absolute;
top: 0;
left: 50%;
width: 50%;
height: 100%;
overflow: hidden;
transition: transform .5s ease-in-out;
z-index: 100;
}

.container .overlay {
position: relative;
left: -100%;
height: 100%;
width: 200%;
background: #3DC795;
color: #fff;
transform: translateX(0);
transition: transform .5s ease-in-out;
}


.container .overlay-left {
position: absolute;
top: 0;
display: flex;
align-items: center;
justify-content: space-around;
flex-direction: column;
padding: 70px 40px;
width: calc(50% - 80px);
height: calc(100% - 140px);
text-align: center;
transform: translateX(-20%);
transition: transform .5s ease-in-out;
}

p {
  margin-right: 40px;
  margin-left: 40px;
}

.container .overlay-right {
position: absolute;
top: 0;
display: flex;
align-items: center;
justify-content: space-around;
flex-direction: column;
padding: 70px 40px;
width: calc(50% - 80px);
height: calc(100% - 140px);
text-align: center;
transform: translateX(0);
transition: transform .5s ease-in-out;
right: 0;
}


h2 {
margin: 0;
}

a {
color: #222;
text-decoration: none;
margin: 15px 0;
font-size: 1rem;
}


button {
border-radius: 20px;
border: 1px solid #3DC795;
background-color: #3DC795;
color: #fff;
font-size: 1rem;
font-weight: bold;
padding: 10px 40px;
letter-spacing: 1px;
text-transform: uppercase;
cursor: pointer;
transition: transform .1s ease-in;
}


button:active {
transform: scale(0.9);
}


button:focus {
outline: none;
}


button.invert {
background-color: transparent;
border-color: #fff;
}


form {
position: absolute;
top: 0;
display: flex;
align-items: center;
justify-content: space-around;
flex-direction: column;
padding: 90px 60px;
width: calc(50% - 120px);
height: calc(100% - 180px);
text-align: center;
background: white;
transition: all .5s ease-in-out;
}


form div {
font-size: 1rem;
}


form input {
background-color: rgb(245, 243, 243);
border: none;
padding: 8px 15px;
margin: 6px 0;
width: calc(100% - 30px);
border-bottom: 1px solid #ddd;
overflow: hidden;
}


form input:focus {
outline: none;
background-color: #fff;
}


.sign-in {
left: 0;
z-index: 2;
}


.sign-up {
left: 0;
z-index: 1;
opacity: 0;
}


.sign-up-active .sign-in {
transform: translateX(100%);
}


.sign-up-active .sign-up {
transform: translateX(100%);
opacity: 1;
z-index: 5;
animation: show .5s;
}


.sign-up-active .overlay-container {
transform: translateX(-100%);
}


.sign-up-active .overlay {
transform: translateX(50%);
}


.sign-up-active .overlay-left {
transform: translateX(0);
}


.sign-up-active .overlay-right {
transform: translateX(20%);
}


@keyframes show {
  0% {
  opacity: 0;
  z-index: 1;
  }
  49% {
  opacity: 0;
  z-index: 1;
  }
  50% {
  opacity: 1;
  z-index: 10;
  }
}

</style>