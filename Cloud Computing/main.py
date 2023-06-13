from fastapi import FastAPI, Request
from pydantic import BaseModel
import math
import numpy as np
import pandas as pd
import tensorflow as tf
from tensorflow import keras
from sklearn.preprocessing import StandardScaler
from sklearn.model_selection import train_test_split

# Define the FastAPI app
app = FastAPI()

# Define the request body model
class PredictionRequest(BaseModel):
    heights: float
    circumferences: float

# Define the API endpoint
@app.post("/")
def predict_age(request: Request, prediction_request: PredictionRequest):
    # Load the trained model
    model = keras.models.load_model('LeafAge.h5')

    # Load the dataset
    df = pd.read_excel('Full Dataset.xlsx')

    # Normalize the new heights and circumferences
    mean_heights = df['heights'].mean()
    std_heights = df['heights'].std()
    mean_circumferences = df['circumferences'].mean()
    std_circumferences = df['circumferences'].std()

    normalized_new_heights = (prediction_request.heights - mean_heights) / std_heights
    normalized_new_circumferences = (prediction_request.circumferences - mean_circumferences) / std_circumferences

    # Make predictions on the normalized data
    prediction = model.predict([[normalized_new_heights, normalized_new_circumferences]])[0][0]

    # Categorize the new ratio
    if prediction_request.circumferences / prediction_request.heights > 3:
        category = "This tree has an EXCELLENT ratio"
    elif 2 <= prediction_request.circumferences / prediction_request.heights <= 3:
        category = "This tree has an IDEAL ratio"
    else:
        category = "This tree has a BAD ratio"

    return {
        "predicted_age": str(math.floor(prediction)),
        "ratio": str(prediction_request.circumferences/prediction_request.heights),
        "category": category
    }

if __name__ == "__main__":
    import uvicorn
    uvicorn.run("main:app", host="0.0.0.0", port=8000)
