<template>
  <div class="auth-form">
    <h2>Login</h2>
    <form @submit.prevent="login">
      <div class="input-group">
        <label for="username">Username</label>
        <input type="text" v-model="username" id="username" required />
      </div>
      <div class="input-group">
        <label for="password">Password</label>
        <input type="password" v-model="password" id="password" required />
      </div>
      <button type="submit">Login</button>
    </form>
    <p>Don't have an account? <router-link to="/register">Register</router-link></p>
  </div>
</template>

<script>
import axios from 'axios'
import { ref } from 'vue'
import { useRouter } from 'vue-router'

export default {
  name: 'UserLogin',
  setup() {
    console.log('UserLogin setup')
    const username = ref('')
    const password = ref('')
    const router = useRouter()

    const login = async () => {
      try {
        const response = await axios.post('http://localhost:8080/api/users/login', {
          username: username.value,
          password: password.value,
        })
        // 假设后端返回 token, 存储在本地
        localStorage.setItem('token', response.data.token)
        router.push('/')
      } catch (error) {
        console.error('Login failed', error)
      }
    }

    return { username, password, login }
  }
}
</script>

<style scoped>
.auth-form {
  max-width: 400px;
  margin: 0 auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 8px;
}
.input-group {
  margin-bottom: 15px;
}
.input-group label {
  display: block;
  margin-bottom: 5px;
}
input {
  width: 100%;
  padding: 8px;
  box-sizing: border-box;
}
button {
  width: 100%;
  padding: 10px;
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}
button:hover {
  background-color: #45a049;
}
</style>
