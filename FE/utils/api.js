import axios from "axios";

const apiBaseUrl = 'http://localhost:8080/api';

// export async function getPostIds() {
//   try {
//     // const response = await axios.get(`${apiBaseUrl}/posts`, {
//     //   params: {fields: ['id']}
//     // });
//     const response = await axios.get(`${apiBaseUrl}/posts`);
//
//     return response.data.map(post => post.id); // 백엔드에서 ID 목록을 반환하도록 가정
//   } catch (error) {
//     console.error("Failed to fetch post IDs:", error);
//     return [];
//   }
// }

// Get a single post by ID with optional field filtering
export async function getPostByID(id, fields = []) {
  try {
    const response = await axios.get(`${apiBaseUrl}/posts/${id}`);
    if (response.data) {
      return { success: true, data: filterFields(response.data, fields) };
    } else {
      console.error("No data returned from API");
      return { success: false, error: "No data returned from API" };
    }
  } catch (error) {
    console.error("Failed to fetch post by ID:", error);
    return { success: false, error: error.message };
  }
}

// Get all posts with optional sorting by date
export async function getAllPosts(fields = []) {
  try {
    const response = await axios.get(`${apiBaseUrl}/posts`);
    if (response.data) {
      const posts = response.data.map(post => filterFields(post, fields));
      posts.sort((post1, post2) => (new Date(post1.date) > new Date(post2.date) ? -1 : 1));
      return { success: true, data: posts };
    } else {
      console.error("No data returned from API");
      return { success: false, error: "No data returned from API" };
    }
  } catch (error) {
    console.error("Failed to fetch all posts:", error);
    return { success: false, error: error.message };
  }
}

// Update a post
export async function editPost(id, data) {
  try {
    const response = await axios.put(`${apiBaseUrl}/posts/${id}`, data);
    return { success: response.status === 200, data: response.data };
  } catch (error) {
    console.error("Failed to edit post:", error);
    return { success: false, error: error.message };
  }
}

// Create a new post
export async function createPost(data) {
  try {
    const response = await axios.post(`${apiBaseUrl}/posts`, data);
    return { success: response.status === 201, data: response.data };
  } catch (error) {
    console.error("Failed to create post:", error);
    return { success: false, error: error.message };
  }
}
export async function deletePost(id) {
  try {
    const response = await axios.delete(`${apiBaseUrl}/posts/${id}`);
    if (response.status === 200 || response.status === 204) {
      // DELETE 요청이 성공적으로 수행되면, 보통 200(OK) 또는 204(No Content) 상태 코드를 반환
      return { success: true, data: 'Post deleted successfully' };
    } else {
      console.error("Failed to delete post at backend:", error);
      return { success: false, error: error.message };
    }
  } catch (error) {
    console.error("Failed to delete post:", error);
    return { success: false, error: error.message };
  }
}


// Utility function to filter fields of a post
function filterFields(post, fields) {
  if (fields.length === 0) {
    return post;
  }
  return fields.reduce((filteredPost, field) => {
    if (post[field] !== undefined) {
      filteredPost[field] = post[field];
    }
    return filteredPost;
  }, {});
}