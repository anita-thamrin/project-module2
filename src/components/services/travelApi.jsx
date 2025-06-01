const GEOAPIFY_API_KEY = "a602b3e7a83d4539bedb463fe1187d9b";

// First, geocode the destination to get lat/lon
async function fetchCoords(destination) {
  const response = await fetch(
    `https://api.geoapify.com/v1/geocode/search?text=${encodeURIComponent(
      destination
    )}&apiKey=${GEOAPIFY_API_KEY}`
  );
  const data = await response.json();
  if (data.features.length === 0) throw new Error("No coordinates found");
  return data.features[0].geometry;
}

// Then fetch places near those coordinates
export async function fetchPlaces(destination) {
  try {
    const { coordinates } = await fetchCoords(destination);
    const lon = coordinates[0];
    const lat = coordinates[1];

    const response = await fetch(
      `https://api.geoapify.com/v2/places?categories=tourism.sights&filter=circle:${lon},${lat},5000&limit=20&apiKey=${GEOAPIFY_API_KEY}`
    );

    const data = await response.json();

    console.log("Geoapify raw response:", data);

    return {
      places: data.features.map((feature) => ({
        name: feature.properties.name || "Unnamed",
        address: feature.properties.formatted || "",
        category: feature.properties.categories?.[0] || "Unknown",
      })),
    };
  } catch (error) {
    console.error("Geoapify API error:", error);
    return { places: [] };
  }
}
