"""dbManager.py

Thin wrapper around pyrebase database operations used by the backend.

DBManager provides convenience methods for reading/writing:
- version (string)
- zones (structure)
- creatures (dict/list)

The class expects a 'config' module with a 'config' dictionary for pyrebase initialization.
"""

try:
    import pyrebase
    import config
except Exception:
    # Allow importing this module in environments where pyrebase or config
    # isn't available (for documentation generation with pdoc).
    pyrebase = None
    config = None


class DBManager:
    """Database manager for pyrebase interactions.

    Usage:
        db = DBManager()
        current_version = db.getVersion()
    """
    def __init__(self):
        """Initialize pyrebase and prepare a database reference.

        If pyrebase/config aren't available this becomes a lightweight stub so
        documentation tools can import the module without error. In a runtime
        environment where pyrebase is installed the behaviour is unchanged.
        """
        if pyrebase is None or config is None:
            # Stubbed attributes for doc-only scenarios.
            self.firebase = None
            self.db = None
            return

        self.firebase = pyrebase.initialize_app(config.config)
        self.db = self.firebase.database()

    def setVersion(self, version):
        """Set the dataset version in the remote database.

        Args:
            version (str): Version string to store under the 'version' node.
        """
        self.db.child("version").set(version)
        print("Version set to " + version)

    def getVersion(self):
        """Retrieve the stored version string from the remote DB.

        Returns:
            str: The stored version string or None if not set.
        """
        return self.db.child("version").get().val()
    
    def getZones(self):
        """Retrieve zone definitions from the remote DB.

        Returns:
            dict|list: The zones structure as stored in the remote database.
        """
        return self.db.child("zones").get().val()
    
    def getCreatures(self):
        """Retrieve creatures from the remote DB.

        Returns:
            dict|list: Creatures as stored in the remote database.
        """
        return self.db.child("creatures").get().val()
    
    def setCreatures(self, creatures):
        """Upload a list or dict of creatures to the remote DB.

        Each creature dict is written under 'creatures/<id>'.

        Args:
            creatures (iterable[dict]): Iterable of creature dictionaries that
                                       must include an 'id' key.
        """
        for creature in creatures:
            self.db.child("creatures").child(creature["id"]).set(creature)