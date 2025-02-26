<template>
  <div class="auth-form">
    <h2>Register</h2>
    <form @submit.prevent="register">
      <div class="input-group">
        <label for="username">Username</label>
        <input type="text" v-model="username" id="username" required />
      </div>
      <div class="input-group">
        <label for="email">Email</label>
        <input type="email" v-model="email" id="email" required />
      </div>
      <div class="input-group">
        <label>Identity</label>
        <div>
          <input type="radio" v-model="identity" value="admin" id="admin" required />
          <label for="admin">Admin</label>
        </div>
        <div>
          <input type="radio" v-model="identity" value="user" id="user" required />
          <label for="user">User</label>
        </div>
      </div>
      <div class="input-group">
        <label for="password">Password</label>
        <input type="password" v-model="password" id="password" required />
      </div>
      <div class="input-group">
        <label for="confirmPassword">Confirm Password</label>
        <input type="password" v-model="confirmPassword" id="confirmPassword" required />
      </div>
      <button type="submit">Register</button>
    </form>
    <p>Already have an account? <router-link to="/login">Login</router-link></p>
  </div>
</template>


<script>
import axios from 'axios'
import { ref } from 'vue'
import { useRouter } from 'vue-router'

export default {
  name: 'UserRegister',
  setup() {
    const username = ref('')
    const email = ref('') // 新增邮箱字段
    const identity = ref('') // 新增身份字段
    const password = ref('')
    const confirmPassword = ref('')
    const router = useRouter()

    const register = async () => {
      if (password.value !== confirmPassword.value) {
        alert('Passwords do not match!')
        return
      }

      try {
        await axios.post('http://localhost:8080/api/users/register', {
          username: username.value,
          email: email.value, // 发送邮箱
          identity: identity.value, // 发送身份
          password: password.value,
        })
        alert('Registration successful')
        router.push('/login')
      } catch (error) {
        console.error('Registration failed', error)
        alert('Registration failed. Please try again.')
      }
    }

    return { username, email, identity, password, confirmPassword, register }
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
input[type="text"],
input[type="email"],
input[type="password"] {
  width: 100%;
  padding: 8px;
  box-sizing: border-box;
}
input[type="radio"] {
  margin-right: 5px;
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
