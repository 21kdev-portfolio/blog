import {editPost} from "../../../../utils/api";

export default async function handler(req, res) {
    const { id } = req.query; // URL에서 id 추출
    if (process.env.NODE_ENV === "development") {
        if (req.method === "POST") {
            try {
                const data = req.body;
                const response = await editPost(id, data);
                // 백엔드 API 응답을 클라이언트에 전달
                res.status(200).json(response.data);
            } catch (error) {
                console.error('API request failed:', error);
                res.status(500).json({error: "Failed to update the post"});
            }
        } else {
            res.status(405).json({error: "Method not allowed"});
        }
    }
    else {
        res.status(200)
            .json({ name: "This route works in development mode only" });
    }
}
