import { getRandomImage } from "../../../utils";
import {createPost, deletePost} from "../../../utils/api";

export default async function handler(req, res) {
  if (process.env.NODE_ENV === "development") {
    if (req.method === "POST") {
      const postData = {
        date: new Date().toISOString(),
        title: req.body.title || "New Blog",
        tagline: req.body.tagline || "Amazing New Blog",
        preview: req.body.preview || "Lorem Ipsum is simply dummy text of the printing and typesetting industry...",
        image: req.body.image || getRandomImage(),
      };
      try {
        const response = await createPost(postData);
        if (response.success) {
          res.status(200).json({ status: "CREATED", post: response.data });
        } else {
          res.status(400).json({ error: "Failed to create post", details: response.error });
        }
      } catch (error) { // 네트워크 오류나 예상치 못한 예외 처리
        console.error('API request failed:', error);
        res.status(500).json({ error: "Internal Server Error" });
      }
    }
    if (req.method === "DELETE") {
      // 포스트 데이터 삭제
      try {
        const { id } = req.body;
        const response = await deletePost(id);
        if (response.success) {
          res.status(200).json({ status: "DELETED" });
        } else {
          res.status(404).json({ error: "Failed to delete post", details: response.error });
        }
      } catch (error) {
        console.error('API request failed:', error);
        res.status(500).json({ error: "Internal Server Error" });
      }
    } else {
      res.status(405).json({ error: "Method not allowed" });
    }
  } else {
    res.status(403).json({ name: "This route works in development mode only" });
  }
}
