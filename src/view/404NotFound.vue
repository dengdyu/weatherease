<template>
  <div class="error-page">
    <div class="error-content">
      <h1>Oops!</h1>
      <p>We can't seem to find the page you're looking for.</p>
      <p><strong>Error code:</strong> 404</p>
      <ul>
        <li><a href="/">Home</a></li>
      </ul>
    </div>
    <img src="@/assets/image/404.jpg" alt="Illustration" class="error-image" />
  </div>
</template>

<style scoped>
.error-page {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #ffffff;
}

.error-content {
  text-align: left;
  width: 40vh;
}

.error-content h1 {
  font-size: 50px;
  color: #333;
}

.error-content p {
  font-size: 26px;
  color: #666;
}

.error-content ul {
  list-style: none;
  padding: 0;
}

.error-content li {
  margin: 5px 0;
}

.error-content a {
  color: #1a73e8;
  text-decoration: none;
}

.error-content a:hover {
  text-decoration: underline;
}

.error-image {
  max-width: 500px;
}
</style>