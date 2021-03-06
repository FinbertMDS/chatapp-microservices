import { AppData } from "./src/core/appData";

export const initialState = {
	user: null,
	contacts: [],
	notification: null,
	currentRoomId: null,
	currentRoomDetail: null,
}

export const actionTypes = {
	SET_USER: "SET_USER",
	SET_CONTACTS: "SET_CONTACTS",
	SET_NOTIFICATION: "SET_NOTIFICATION",
	SET_CURRENT_ROOM_ID: "SET_CURRENT_ROOM_ID",
	SET_CURRENT_ROOM_DETAIL: "SET_CURRENT_ROOM_DETAIL",
}

const reducer = (state, action) => {
	switch (action.type) {
		case actionTypes.SET_USER:
			AppData.user = action.user;
			return {
				...state,
				user: action.user
			}
		case actionTypes.SET_CONTACTS:
			return {
				...state,
				contacts: action.contacts
			}
		case actionTypes.SET_NOTIFICATION:
			return {
				...state,
				notification: action.notification
			}
		case actionTypes.SET_CURRENT_ROOM_ID:
			return {
				...state,
				currentRoomId: action.currentRoomId
			}
		case actionTypes.SET_CURRENT_ROOM_DETAIL:
			return {
				...state,
				currentRoomDetail: action.currentRoomDetail
			}
		default:
			return state;

	}
}

export default reducer;